/*
 * (C) Copyright Itude Mobile B.V., The Netherlands
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.itude.mobile.mobbl.core.configuration.resources;

import java.util.ArrayList;
import java.util.List;

import android.test.ApplicationTestCase;

import com.itude.mobile.android.util.AssetUtil;
import com.itude.mobile.android.util.FileUtil;
import com.itude.mobile.mobbl.core.MBApplicationCore;
import com.itude.mobile.mobbl.core.configuration.mvc.MBBundleDefinition;

public class MBResourceConfigurationParserTest extends ApplicationTestCase<MBApplicationCore>
{

  private MBResourceConfigurationParser parser;

  private final String[]                resourceIds    = {"config", "endpoints", "ICON-tab_1", "ICON-tab_2"};

  private final String[]                resourceValues = {"file://config.xml", "file://endpoints.xml", "file://ic_1.png", "file://ic_2.png"};
  private final String[]                bundleIds      = {"nl", "fr"};
  private final String[]                bundleValues   = {"file://texts-nl.xml", "file://texts-nl.xml"};

  private byte[]                        data;

  public MBResourceConfigurationParserTest()
  {
    super(MBApplicationCore.class);
  }

  @Override
  protected void setUp() throws Exception
  {
    parser = new MBResourceConfigurationParser();

    ArrayList<String> _resourceAttributes = new ArrayList<String>();
    _resourceAttributes.add("xmlns");
    _resourceAttributes.add("id");
    _resourceAttributes.add("url");
    _resourceAttributes.add("cacheable");
    _resourceAttributes.add("ttl");
    parser.setResourceAttributes(_resourceAttributes);

    ArrayList<String> _bundleAttributes = new ArrayList<String>();
    _bundleAttributes.add("xmlns");
    _bundleAttributes.add("languageCode");
    _bundleAttributes.add("url");
    parser.setBundleAttributes(_bundleAttributes);

    createApplication();
    FileUtil.getInstance().setContext(getContext());
    data = AssetUtil.getInstance().getByteArray("unittests/testresources.xml");
  }

  public void testResourceParsing()
  {
    MBResourceConfiguration configuration = (MBResourceConfiguration) parser.parseData(data, "Resources");

    // Test resources
    for (int i = 0; i < resourceIds.length; i++)
    {
      MBResourceDefinition def = configuration.getResourceWithID(resourceIds[i]);
      assertEquals(resourceIds[i], def.getResourceId());
      assertEquals(resourceValues[i], def.getUrl());

      // TODO add some test to check cacheable and ttl 
    }

  }

  public void testBundleParsing()
  {
    // TODO create tests for bundles with same language

    MBResourceConfiguration configuration = (MBResourceConfiguration) parser.parseData(data, "Resources");

    // Test bundles
    for (int j = 0; j < bundleIds.length; j++)
    {
      List<MBBundleDefinition> bundles = configuration.getBundlesForLanguageCode(bundleIds[j]);
      MBBundleDefinition bundle = bundles.get(0);
      assertEquals(bundleIds[j], bundle.getLanguageCode());
      assertEquals(bundleValues[j], bundle.getUrl());
    }
  }

}
