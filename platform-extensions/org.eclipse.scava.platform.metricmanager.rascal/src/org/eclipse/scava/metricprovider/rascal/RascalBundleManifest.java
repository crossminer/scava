/*******************************************************************************
 * Copyright (c) 2017 Centrum Wiskunde & Informatica
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.rascal;

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

