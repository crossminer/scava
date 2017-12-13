/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jurgen Vinju - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.rascal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.Bundle;
import org.rascalmpl.interpreter.utils.RascalManifest;

/**
 * See @link {@link RascalManifest}. This class adds support for OSGI bundles.
 */
public class RascalBundleManifest extends RascalManifest {

  public List<String> getSourceRoots(Bundle project) {
    return getManifestSourceRoots(manifest(project));
  }
  
  public String getMainModule(Bundle project) {
    return getManifestMainModule(manifest(project));
  }
  
  public String getMainFunction(Bundle project) {
    return getManifestMainFunction(manifest(project));
  }

  public List<String> getRequiredBundles(Bundle project) {
    return getManifestRequiredBundles(manifest(project));
  }
  
  public List<String> getRequiredLibraries(Bundle project) {
	  return getManifestRequiredLibraries(manifest(project));
  }
  
  private InputStream manifest(Bundle bundle) {
    URL rascalMF = bundle.getResource(META_INF_RASCAL_MF);

    try {
      if (rascalMF != null) {
        return FileLocator.openStream(bundle, new Path(META_INF_RASCAL_MF), false);
      }
    }
    catch (IOException e) {
      // do nothing, it's expected that some bundles do not have RASCAL.MF files
    }
    
    return null;
  }
  
  
  
  public boolean hasManifest(Bundle bundle) {
    return hasManifest(manifest(bundle));
  }
}

