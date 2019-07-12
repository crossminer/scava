package org.eclipse.scava.business.impl.APIMigration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.eclipse.aether.artifact.Artifact;
import org.eclipse.scava.business.integration.MavenLinkAllRepository;
import org.eclipse.scava.business.model.MavenLibrary;
import org.eclipse.scava.business.model.MavenLinkAll;
import org.eclipse.scava.business.model.migration.Delta;
import org.maracas.data.Detection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import nl.cwi.swat.aethereal.MavenCollector;
import nl.cwi.swat.aethereal.MavenDataset;
import nl.cwi.swat.aethereal.MigrationInfo;

@Service
public class MigrationService {
	private static final Logger logger = LoggerFactory.getLogger(MigrationService.class);
//	@Autowired
//	private AetherDownloader downloader;
	@Autowired
	private MavenLinkAllRepository mavenLinkAllRepo;
	@Autowired
	private MavenCollector collector;
	//@Value("${migration.maven.path}")
	private String mavenRepoPath;

	public Delta createDelta(MavenLibrary libV1, MavenLibrary libV2, org.eclipse.scava.business.model.Artifact art) {
//		Artifact v = downloader.downloadArtifact(new DefaultArtifact(
//				String.format("%s:%s:%s", libV1.getGroupid(), libV1.getArtifactid(), libV1.getVersion())));
//		libV1.setArtifact(v);
		return null;
	}

	public void importer(String filename) {
		AtomicInteger cont = new AtomicInteger();
		ArrayList<MavenLinkAll> results = Lists.newArrayList();
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			stream.forEach(z -> {
				if ((cont.get() % 1000) == 0) {
					logger.info("Iteration {} over 10.000.000", cont.get());
					mavenLinkAllRepo.save(results);
					results.removeIf(k -> true);
				}
				cont.incrementAndGet();
				try {
					results.add(new MavenLinkAll(z.split(",")[0].replace("\"", ""), z.split(",")[1].replace("\"", "")));
				} catch (Exception e) {
					logger.error("Error in parsing line {}", cont.get());
				}
			});
			logger.info("Imported {} maven links_all", results.size());
			mavenLinkAllRepo.save(results);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public MigrationInfo getMigrationPairs(String coordsV1, String coordV2){
		MavenDataset md = new MavenDataset(mavenRepoPath, collector);
		return  md.getVersions(coordsV1, coordV2).get(0);
	}
	public List<Artifact> getClients(String coordsV1){
		MavenDataset md = new MavenDataset(mavenRepoPath, collector);
		return  md.getClients(coordsV1);
	}

	public void ingternal() {
//		String libv1 = "junit:junit:4.10";
//		String libv2 = "junit:junit:4.12";
//		String client = "";
//		// getDetections
//		List<Detection> decs = Lists.newArrayList();
//		MavenDataset md = new MavenDataset(mavenRepoPath, collector);
//		Multimap<Artifact, Artifact> links = md.getVersions(libv1, libv2);
//		for (Artifact art : links.keySet()) {
//			art = downloader.downloadArtifactTo(art,mavenRepoPath);
//		}
//		Collection<Artifact> clientsV1 = links.get(links.keySet().stream().findFirst().get());
//		Map <Artifact, List<Detection>> clientDetections  = Maps.newHashMap();
//		for (Artifact artifact : clientsV1) {
//			Set<String> v = getDextection(libv1,libv2,artifact).stream()
//					.map(Detection::getOldLibraryLocation)
//					.collect(Collectors.toSet());
//			Set<String> intersec = Sets.intersection(v, Sets.newHashSet(decs));
////			if(v
//					
//		}
//		for (Artifact artifact : clientsV1) {
//			
//		}
	}

	private List<Detection> getDextection(String libv1, String libv2, Artifact artifact) {
		// TODO Auto-generated method stub
		return null;
	}
//	public List<Detection> calculateDetection(Artifact art){
//		//TODO
//		Maracas m = new Maracas();
//		Collection<Detection> detections = m.getDetections("/Users/juri/Desktop/report/", "gitlfs-common-0.8.0");
//		detections.forEach(z->System.out.println(z.getType()));
//		return Lists.newArrayList(detections);
//	}
}
