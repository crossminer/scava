package org.eclipse.scava.workflow.restmule.core.cache;

import static com.google.common.net.HttpHeaders.ETAG;
import static com.google.common.net.HttpHeaders.LAST_MODIFIED;
import static com.google.common.net.HttpHeaders.LINK;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.DateTools.Resolution;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.eclipse.scava.workflow.restmule.core.session.ISession;
import org.eclipse.scava.workflow.restmule.core.util.OkHttpUtil;

import io.reactivex.annotations.NonNull;
import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 
 * {@link AbstractCacheManager}
 * <p>
 * 
 * @version 1.0.0
 *
 */
public abstract class AbstractCacheManager implements ICache {

	private static final Logger LOG = LogManager.getLogger(AbstractCacheManager.class);

	public static final String USER_DIR = "user.dir";
	public static final String BASE_FOLDER = "restmule";
	public static final String BASEPATH = System.getProperty(USER_DIR) + File.separatorChar + "." + BASE_FOLDER
			+ File.separatorChar;

	private String indexDir;

	/** OkHttp Properties */
	private final static int cacheSize = 50 * 1024 * 1024; // 50 MiB
	private static Cache cache;

	// FIXME for testing purposes

	public Cache getOkHttpCache() {
		if (cache == null) {
			cache = new Cache(new File(this.indexDir), cacheSize);
		}
		return cache;
	}

	/** CONSTRUCTOR */

	protected AbstractCacheManager(@NonNull String agentName) {
		this.indexDir = BASEPATH + agentName;
		if (cache == null)
			cache = this.getOkHttpCache();
		File file = new File(this.indexDir);
		if (!file.exists())
			file.mkdirs();
	}

	/** PRIVATE METHODS */

	private String getIndexDir() {
		return this.indexDir;
	}

	private IndexWriter getWriter() throws IOException {
		return getWriter(this.indexDir);
	}

	private IndexWriter getWriter(String indexDir) throws IOException {
		Directory indexDirectory = getDirectory(indexDir);
		StandardAnalyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(indexDirectory, config);
		return writer;
	}

	private Directory getDirectory() throws IOException {
		return getDirectory(this.indexDir);
	}

	private Directory getDirectory(String indexDir) throws IOException {
		Directory indexDirectory = MMapDirectory.open(new File(indexDir).toPath()); // FIXME
																					// NIOFSDirectory/FSDirectory?
		return indexDirectory;
	}

	private IndexSearcher getIndexSearcher() throws IOException {
		Directory indexDirectory = MMapDirectory.open(new File(getIndexDir()).toPath());
		return new IndexSearcher(DirectoryReader.open(indexDirectory));
	}

	private QueryBuilder getQueryBuilder() {
		return new QueryBuilder(new StandardAnalyzer());
	}

	private TopDocs search(Query query, IndexSearcher indexSearcher) throws IOException {
		return indexSearcher.search(query, 1);
	}

	private void closeSearcher(IndexSearcher indexSearcher) {
		if (indexSearcher != null) {
			try {
				indexSearcher.getIndexReader().close();
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		}
	}

	private void closeWriter(IndexWriter writer) {
		if (writer != null) {
			try {
				int numDocs = writer.numDocs();
				LOG.info(String.format("Index has %s entr%s", numDocs, numDocs == 1 ? "y" : "ies"));
				writer.close();
			} catch (IOException e) {
				LOG.error(e);
				e.printStackTrace();
			} finally {
				try {
					getDirectory().close();
				} catch (IOException e) {
					LOG.error(e);
					e.printStackTrace();
				}
			}
		}
	}

	private boolean isExpired(Document doc) {
		long timeout = new Date().getTime() + 1000;
		try {
			timeout = DateTools.stringToTime(doc.get(Indexable.TIMEOUT));
		} catch (ParseException e) {
			LOG.error(e.getMessage());
		}
		return new Date(timeout).before(new Date());
	}

	/** ICACHE METHODS */

	@Override
	public Response put(Response response, ISession session) throws IOException {
		Indexable indexable = new IndexEntry(response, session);
		if (!exists(indexable.getId())) {
			index(indexable);
		}
		return (Response) indexable.getResponseAdapter().response();
	}

	@Override
	public boolean exists(Request request, ISession session) {
		return exists(IndexEntry.getId(request, session));
	}

	@Override
	public boolean exists(Response response, ISession session) {
		return exists(IndexEntry.getId(response, session));
	}

	private boolean exists(String id) {
		IndexSearcher indexSearcher = null;
		boolean exists = false;
		Query query = getQueryBuilder().createPhraseQuery(Indexable.ID, id);
		try {
			indexSearcher = getIndexSearcher();
			TopDocs docs = search(query, indexSearcher);
			if (docs.scoreDocs != null && docs.scoreDocs.length > 0) {
				Document doc = indexSearcher.doc(docs.scoreDocs[0].doc);
				if (isExpired(doc)) {
					IndexWriter writer = null;
					try {
						writer = getWriter();
						writer.deleteDocuments(query);
					} catch (Exception e) {
						LOG.error(e.getMessage());
					} finally {
						closeWriter(writer);
					}
				} else {
					exists = true;
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		} finally {
			closeSearcher(indexSearcher);
		}
		return exists;
	}

	@Override
	public Response load(Response response, ISession session) {
		return load(response.request(), session);
	}

	@Override
	public Response load(Request request, ISession session) {
		String id = IndexEntry.getId(request, session);
		Query query = getQueryBuilder().createPhraseQuery(Indexable.ID, id);

		Set<String> params = new HashSet<String>();
		params.add(Indexable.CONTENTS);
		params.add(Indexable.CONTENT_TYPE);
		params.add(Indexable.ETAG);
		params.add(Indexable.LAST_MODIFIED);
		params.add(Indexable.LINK);

		HashMap<String, Object> entrySet = getParams(query, params);
		Headers.Builder builder = new Headers.Builder();

		String etag = (String) entrySet.get(Indexable.ETAG);
		if (etag.length() > 0)
			builder.add(ETAG, etag);

		String lastModif = (String) entrySet.get(Indexable.LAST_MODIFIED);
		if (lastModif.length() > 0)
			builder.add(LAST_MODIFIED, lastModif);

		String link = (String) entrySet.get(Indexable.LINK);
		if (link != null && link.length() > 0)
			builder.add(LINK, link);

		Headers headers = builder.build();
		// JsonStringEncoder encoder = JsonStringEncoder.getInstance();
		LOG.info(((String) entrySet.get(Indexable.CONTENTS)).substring(0, 100));
		return OkHttpUtil.response(request, (String) entrySet.get(Indexable.CONTENTS),
				(String) entrySet.get(Indexable.CONTENT_TYPE), headers);
	}

	private int retryCount = 1;

	@Override
	public void clear() {

		File[] files = new File(this.indexDir).listFiles();
		boolean retry = false;

		if (files.length > 0) {
			for (File file : files) {
				try {
					FileDeleteStrategy.FORCE.delete(file);
				} catch (IOException e) {
					System.err.println("failed to delete: " + file.getPath());
					retry = true;
				}
				// System.err.println("failed to remove file: " +
				// file.getPath());
			}
		}

		if (retry && retryCount > 0) {
			System.err.println("retrying (" + retryCount + ") to delete in 2 seconds...");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			retryCount--;
			clear();
		}

	}

	/** UTILIS */

	private HashMap<String, Object> getParams(Query query, Set<String> returnFields) throws NoSuchElementException { // FIXME
		IndexSearcher indexSearcher = null;
		HashMap<String, Object> result = new HashMap<>();
		try {
			indexSearcher = getIndexSearcher();
			final int unique = 1;
			TopDocs search = indexSearcher.search(query, unique);
			if (search != null && search.scoreDocs.length == unique) {
				ScoreDoc scoreDoc = search.scoreDocs[0];
				Document doc = indexSearcher.doc(scoreDoc.doc, returnFields);
				for (String element : returnFields) {
					result.put(element, doc.get(element));
				}
			} else {
				throw new NoSuchElementException("No matching results");
			}
		} catch (IOException e) {
			LOG.error(e.getMessage());
		} finally {
			closeSearcher(indexSearcher);
		}
		return result;
	}

	private synchronized void index(Indexable indexable) throws IOException {
		IndexWriter writer = null;
		try {
			writer = getWriter();

			Document document = new Document();
			ArrayList<Field> fields = new ArrayList<>();

			fields.add(new TextField(Indexable.ID, indexable.getId(), Store.YES));
			fields.add(new TextField(Indexable.CONTENTS, indexable.getContents(), Store.YES));
			fields.add(new TextField(Indexable.ETAG, indexable.getEtag(), Store.YES));
			fields.add(new TextField(Indexable.LAST_MODIFIED, indexable.getLastModified(), Store.YES));
			fields.add(new TextField(Indexable.TIMEOUT, DateTools.dateToString(indexable.getTimeout(), Resolution.HOUR),
					Store.YES));
			fields.add(new TextField(Indexable.CONTENT_TYPE, indexable.contentType(), Store.YES));
			if (indexable.link() != null && indexable.link().length() > 0) {
				fields.add(new TextField(Indexable.LINK, indexable.link(), Store.YES));
			}

			for (Field field : fields)
				document.add(field);

			writer.addDocument(document);
			writer.commit();
		} finally {
			closeWriter(writer);
		}
	}
}
