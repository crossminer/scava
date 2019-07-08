package org.eclipse.scava.business.impl.APIMigration;

import java.util.List;

import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.scava.business.integration.MavenLibraryRepository;
import org.eclipse.scava.business.integration.MavenLinkAllRepository;
import org.eclipse.scava.business.model.MavenLibrary;
import org.eclipse.scava.business.model.MavenLinkAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import nl.cwi.swat.aethereal.MavenCollector;
import nl.cwi.swat.aethereal.MavenCollectorQuery;

@Service
public class MongoCollector implements MavenCollector{

	@Autowired
	private MavenLibraryRepository mavenLibraryRepository;
	@Autowired
	private MavenLinkAllRepository mavenLinkAllRepository;
	
	@Override
	public List<Artifact> collectAvailableVersions(String coordinates) {
		List<MavenLibrary> libs = mavenLibraryRepository.findByGroupidAndArtifactidOrderByReleasedateDesc(
				coordinates.split(":")[0], 
				coordinates.split(":")[1]);
		List<Artifact> result = Lists.newArrayList();
		for (MavenLibrary mavenLibrary : libs) {
			Artifact artifact = new DefaultArtifact(
					String.format("%s:%s:%s",
						mavenLibrary.getGroupid(),
						mavenLibrary.getArtifactid(),
						mavenLibrary.getVersion()
					));
			result.add(artifact);
		}
		return result;
	}

	@Override
	public List<Artifact> collectClientsOf(Artifact artifact) {
		String coord = String.format("%s:%s:%s", artifact.getGroupId(),artifact.getArtifactId(), artifact.getVersion());
		List<MavenLinkAll> libs = mavenLinkAllRepository.findByCoordinateDep(coord);
		List<Artifact> result = Lists.newArrayList();
		for (MavenLinkAll mavenLinkAll : libs) {
			result.add(new DefaultArtifact(mavenLinkAll.getCoordinate()));
		}
		return result;
	}

	@Override
	public Multimap<Artifact, Artifact> collectClientsOf(String coord) {
		List<MavenLinkAll> libs = mavenLinkAllRepository.findByCoordinateDep(coord);
		Multimap<Artifact, Artifact> result =  ArrayListMultimap.create();
		for (MavenLinkAll mavenLinkAll : libs) {
			result.put(new DefaultArtifact(coord),
					new DefaultArtifact(mavenLinkAll.getCoordinate()));
		}
		return result;
	}

	@Override
	public List<Artifact> collectLibrariesMatching(MavenCollectorQuery query) {
		throw new UnsupportedOperationException("Not yet");
	}

	@Override
	public boolean checkArtifact(String coordinate) {
		return mavenLibraryRepository.findOneByGroupidAndArtifactidAndVersion(
				coordinate.split(":")[0], coordinate.split(":")[1], coordinate.split(":")[2]) != null;
	}

}
