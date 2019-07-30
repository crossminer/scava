package org.eclipse.scava.business.impl.APIMigration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.scava.business.integration.DeltaRepository;
import org.eclipse.scava.business.integration.DetectionMetadataRepository;
import org.eclipse.scava.business.integration.MavenLibraryRepository;
import org.eclipse.scava.business.integration.MavenLinkAllRepository;
import org.eclipse.scava.business.model.MavenLibrary;
import org.eclipse.scava.business.model.MavenLinkAll;
import org.eclipse.scava.business.model.migration.Delta;
import org.eclipse.scava.business.model.migration.DetectionMetaData;
import org.eclipse.scava.business.model.migration.DetectionResult;
import org.maracas.Maracas;
import org.maracas.data.Detection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.common.collect.Lists;

import nl.cwi.swat.aethereal.AetherDownloader;
import nl.cwi.swat.aethereal.MavenCollector;
import nl.cwi.swat.aethereal.MavenDataset;
import nl.cwi.swat.aethereal.MigrationInfo;

@Service
public class MigrationService {
	private static final Logger logger = LoggerFactory.getLogger(MigrationService.class);
	private AetherDownloader downloader = new AetherDownloader(20);
	@Autowired
	private MavenLinkAllRepository mavenLinkAllRepo;
	@Autowired
	private MavenLibraryRepository mavenRepo;
	@Autowired
	private MavenCollector collector;
	@Value("${migration.maven.path}")
	private String mavenRepoPath;
	@Value("${migration.deltas.path}")
	private String deltasBasePath;
	private Maracas maracas = new Maracas();
	@Autowired
	private DeltaRepository deltaRepository;
	@Autowired
	private DetectionMetadataRepository detectionRepo;
	@Value("${migration.maven.max.training.clients}")
	private long _MAX_TRAINING_CLIENT;

	public Delta storeDelta(MavenLibrary libV1, MavenLibrary libV2) throws Exception {
		Delta delta = deltaRepository.findOneByCoordinate1AndCoordinate2(libV1.getCoordinate(), libV2.getCoordinate());
		if (delta == null)
			try {
				logger.info("KB is computing the delta");
				String deltaPath = createDelta(libV1, libV2);
				delta = new Delta(libV1.getCoordinate(), libV2.getCoordinate(), deltaPath);
				deltaRepository.save(delta);
				logger.info("Delta is computed");
				return delta;
			} catch (Exception e) {
				logger.error("error in computing the delta model");
				throw e;
			}
		else {
			logger.info("Delta is already computed");
			return delta;
		}
	}

	public Delta storeDelta(String libV1, String libV2) throws Exception {
		MavenLibrary mvn1 = mavenRepo.findOneByGroupidAndArtifactidAndVersion(libV1.split(":")[0], libV1.split(":")[1],
				libV1.split(":")[2]);
		MavenLibrary mvn2 = mavenRepo.findOneByGroupidAndArtifactidAndVersion(libV2.split(":")[0], libV2.split(":")[1],
				libV2.split(":")[2]);
		return storeDelta(mvn1, mvn2);
	}

	public DetectionMetaData createDetection(String libV1, String libV2, String clientM3) {
		DetectionMetaData det = detectionRepo.findOneByCoordinate1AndCoordinate2AndClientM3(libV1, libV2, clientM3);
		if (det == null) {
			Delta d;
			try {
				d = storeDelta(libV1, libV2);
				logger.info("Computing detections: {}", clientM3);
				List<Detection> detects = maracas.getDetections(clientM3,
						Paths.get(deltasBasePath, d.getDeltaPath()).toString());
				DetectionMetaData dmd = new DetectionMetaData(libV1, libV2, clientM3, detects);
				logger.info("Computed detections: {} - {}", detects.size(), clientM3);
				return dmd;
			} catch (Exception e) {
				logger.error(e.getMessage());
				return det;
			}
		} else
			return det;
	}

	public DetectionMetaData storeDetections(String libV1, String libV2, String clientM3, String clientSource)
			throws Exception {
		DetectionMetaData dmd = createDetection(libV1, libV2, clientM3);
		dmd.setClientSource(clientSource);
		detectionRepo.save(dmd);
		return dmd;
	}

	private Artifact migratedClient(MigrationInfo mi, String clientName) throws Exception {
		String[] names = clientName.split(":");
		for (Artifact art : mi.clientsV2)
			if (art.getGroupId().equals(names[0]) && art.getArtifactId().equals(names[1]))
				return art;
		throw new Exception("Artifact not found");
	}

	public MultiValueMap<String, String> recommendsSnippet(String libV1, String libV2, String clientM3)
			throws Exception {
		List<DetectionResult> drs = getDetecionResults(libV1, libV2, clientM3);
		MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
		for (DetectionResult detectionResults : drs) {
			for (DetectionMetaData dmd : detectionResults.getDetectionsMD()) {
				for (Detection detection : dmd.getDetections()) {
					try {
					String s = getCode(dmd.getClientSource(), detection.getClientLocation());
					if (s != null && !s.isEmpty()) {
						result.add(detectionResults.getDetection().getClientLocation(), s);
						// TODO fix it
						break;
					}
					}catch(Exception e) {
						logger.error(e.getMessage());
					}
				}
			}

		}
		return result;

	}

	public List<DetectionResult> getDetecionResults(String libV1, String libV2, String clientM3) throws Exception {
		MigrationInfo mig = getMigrationPairs(libV1, libV2);
		return actualizeDetectionResult(getDetecionResults(mig, libV1, libV2, clientM3), mig);
	}

	public List<DetectionResult> getDetecionResults(MigrationInfo mig, String libV1, String libV2, String clientM3)
			throws Exception {
		logger.info("Computing detections on the current project {}", clientM3);
		DetectionMetaData myDetyects = createDetection(libV1, libV2, clientM3);
		logger.info("Computed detections on the current project {}", clientM3);
		List<DetectionMetaData> clientsImpact = getTrainingDetections(libV1, libV2, mig.clientsV1, clientM3);

		List<DetectionResult> result = Lists.newArrayList();
		List<DetectionMetaData> dmds = clientsImpact;
		for (Detection projectDetection : myDetyects.getDetections()) {
			DetectionResult app = new DetectionResult();
			app.setDetection(projectDetection);
			for (DetectionMetaData detectionMD : dmds) {
				List<Detection> listDetectections = Lists.newArrayList();
				for (Detection clientDetenction : detectionMD.getDetections())
					if (clientDetenction.getOldLibraryLocation().equals(projectDetection.getOldLibraryLocation()))
						listDetectections.add(clientDetenction);
				if (listDetectections.size() > 0) {
					detectionMD.setDetections(listDetectections);
					app.getDetectionsMD().add(detectionMD);
				}
			}
			if (app.getDetectionsMD().size() > 0)
				result.add(app);
		}
		return result;
	}

	public List<DetectionResult> actualizeDetectionResult(List<DetectionResult> detRes, MigrationInfo mig)
			throws Exception {
		for (DetectionResult detectionResult : detRes) {
			for (DetectionMetaData dmd : detectionResult.getDetectionsMD()) {
				try {
					Artifact neew = migratedClient(mig, dmd.getClientSource());
					dmd.setClientSource(getCoordinate(neew));
				} catch (Exception e) {
					logger.error("extracting snippets error");
				}
			}
		}
		return detRes;
	}

	public List<DetectionMetaData> getTrainingDetections(String libV1, String libV2, List<Artifact> artifacts,
			String clientM3) throws Exception {
		
		List<DetectionMetaData> result = Lists.newArrayList();
		int i = 1;
		for (Artifact client : artifacts.stream().limit(_MAX_TRAINING_CLIENT ).collect(Collectors.toList())) {
			try {
				logger.info("{} of {}", i++, artifacts.size());
				String trainingM3 = getM3model(
						new MavenLibrary(client.getArtifactId(), client.getGroupId(), client.getVersion()));
				DetectionMetaData inner = storeDetections(libV1, libV2, trainingM3, getCoordinate(client));
				result.add(inner);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		return result;
	}

	private String getCoordinate(Artifact clientV1) {
		return String.format("%s:%s:%s", clientV1.getGroupId(), clientV1.getArtifactId(), clientV1.getVersion());
	}

	/**
	 * It computes the delta model
	 * 
	 * @param libV1 the maven library v1
	 * @param libV2 the maven library v2
	 * @return the path of the delta model
	 * @throws Exception 
	 */
	public String createDelta(MavenLibrary libV1, MavenLibrary libV2) throws Exception {
		String delta = String.format("%s__%s", libV1.getCoordinate().replace(":", "_"),
				libV2.getCoordinate().replace(":", "_"));
		String deltaFullPath = Paths.get(deltasBasePath, delta).toString();
		if (Files.exists(Paths.get(deltaFullPath), new LinkOption[] { LinkOption.NOFOLLOW_LINKS }))
			return delta;
		String libV1M3, libV2M3;
		try {
			libV1M3 = getM3model(libV1);
			libV2M3 = getM3model(libV2);
			boolean bDelta = maracas.storeDelta(libV1M3, libV2M3, deltaFullPath);
			logger.info("Delta store: " + bDelta);
			if (!bDelta)
				throw new Exception("Error in computing/storing delta");
		} catch (Exception e) {
			logger.error("error in computing delta model");
			throw e;
		}
		return delta;
	}

	/**
	 * 
	 * @param delta    is the path of delta model
	 * @param clientM3 is the path of the current m3 model
	 * @return a list of detections
	 * @throws Exception delta/detection error
	 */
	public List<Detection> getDetections(MavenLibrary libV1, MavenLibrary libV2, String clientM3) throws Exception {
		Delta d = storeDelta(libV1, libV2);
		return maracas.getDetections(clientM3, d.getDeltaPath());
	}

	public List<Detection> getDetections(String libV1, String libV2, String clientM3) throws Exception {
		Delta d = storeDelta(libV1, libV2);
		List<Detection> detects = maracas.getDetections(clientM3,
				Paths.get(deltasBasePath, d.getDeltaPath()).toString());
		return detects;
	}

	public List<Detection> getDetections(String delta, String clientM3) {
		return maracas.getDetections(clientM3, delta);
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

	public MigrationInfo getMigrationPairs(String coordsV1, String coordV2) {
		MavenDataset md = new MavenDataset(mavenRepoPath, collector);
		return md.getVersions(coordsV1, coordV2).get(0);
	}
	
	public List<MigrationInfo> getMigrationMatrix(String coordsV1) {
		MavenDataset md = new MavenDataset(mavenRepoPath, collector);
		return md.getVersions(coordsV1);
	}

	public List<Artifact> getClients(String coordsV1) {
		MavenDataset md = new MavenDataset(mavenRepoPath, collector);
		return md.getClients(coordsV1);
	}

	private String getM3model(MavenLibrary lib) throws Exception {
		// TODO CHECK HERE
		Artifact v1 = downloader.downloadArtifactTo(
				new DefaultArtifact(
						String.format("%s:%s:%s", lib.getGroupid(), lib.getArtifactid(), lib.getVersion())),
				mavenRepoPath);
		lib.setArtifact(v1);
		String libM3 = lib.getArtifact().getFile().getAbsolutePath() + ".m3";
		if (!Files.exists(Paths.get(libM3), new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) {
			String libJar = lib.getArtifact().getFile().getAbsolutePath();
			boolean bLib1 = maracas.storeM3(libJar, libM3);
			logger.info("Lib1 store: " + bLib1);
			if (!bLib1) {
				logger.error("error computing {} m3 model", lib.getCoordinate());
				throw new Exception();
			}
		}
		return libM3;
	}
	public String getM3modelFromJar(String jarPath) throws Exception {
		// TODO CHECK HERE
		String libM3 = jarPath + ".m3";
		if (!Files.exists(Paths.get(libM3), new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) {
			String libJar = jarPath;
			boolean bLib1 = maracas.storeM3(libJar, libM3);
			logger.info("Lib1 store: " + bLib1);
			if (!bLib1) {
				logger.error("error computing {} m3 model", jarPath);
				throw new Exception();
			}
		}
		return libM3;
	}

	private String getSources(MavenLibrary lib) throws Exception {
		return getSources(getCoordinate(lib.getArtifact()));
	}

	private String getSources(String coord) throws Exception {
		String[] coords = coord.split(":");
		Artifact v1 = downloader.downloadArtifactTo(
				new DefaultArtifact(coords[0], coords[1], "sources", "jar", coords[2]), mavenRepoPath);
		String libM3 = v1.getFile().getAbsolutePath() + "_src.m3";
		if (!Files.exists(Paths.get(libM3), new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) {
			String jarDir = libM3 + "_dir";
			maracas.unzipJar(v1.getFile().getAbsolutePath(), jarDir);
			boolean bLib1 = maracas.storeM3FromDir(jarDir, libM3);
			logger.info("Lib1 store: " + bLib1);
			if (!bLib1) {
				logger.error("error computing {} m3 model", coord);
				throw new Exception();
			}
		}
		return libM3;
	}

	public String getCode(String jarFile, String location) throws Exception {
		String sources = getSources(jarFile);
		String result = maracas.getCodeFromM3(sources, location);
		return result.replace(mavenRepoPath, "");
	}
}
