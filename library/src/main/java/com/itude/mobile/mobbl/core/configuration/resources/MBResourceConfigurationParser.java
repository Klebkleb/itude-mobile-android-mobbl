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

import com.itude.mobile.android.util.StringUtil;
import com.itude.mobile.mobbl.core.configuration.MBConfigurationParser;
import com.itude.mobile.mobbl.core.configuration.MBDefinition;
import com.itude.mobile.mobbl.core.configuration.mvc.MBBundleDefinition;
import com.itude.mobile.mobbl.core.util.MBConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link MBConfigurationParser} Class to parse a resource configuration file
 */
public class MBResourceConfigurationParser extends MBConfigurationParser {
    private List<String> _resourceAttributes;
    private List<String> _bundleAttributes;
    private List<String> _statedResourceAttributes;
    private List<String> _layeredResourceAttributes;
    private List<String> _itemAttributes;

    @Override
    public MBDefinition parseData(byte[] data, String documentName) {
        if (_resourceAttributes == null) {
            _resourceAttributes = new ArrayList<String>();
            _resourceAttributes.add("xmlns");
            _resourceAttributes.add("id");
            _resourceAttributes.add("type");
            _resourceAttributes.add("url");
            _resourceAttributes.add("color");
            _resourceAttributes.add("cacheable");
            _resourceAttributes.add("ttl");
            _resourceAttributes.add("align");
        }
        if (_bundleAttributes == null) {
            _bundleAttributes = new ArrayList<String>();
            _bundleAttributes.add("xmlns");
            _bundleAttributes.add("languageCode");
            _bundleAttributes.add("url");
        }
        if (_statedResourceAttributes == null) {
            _statedResourceAttributes = new ArrayList<String>();
            _statedResourceAttributes.add("xmlns");
            _statedResourceAttributes.add("id");
            _statedResourceAttributes.add("viewType");
        }
        if (_layeredResourceAttributes == null) {
            _layeredResourceAttributes = new ArrayList<String>();
            _layeredResourceAttributes.add("xmlns");
            _layeredResourceAttributes.add("id");
        }
        if (_itemAttributes == null) {
            _itemAttributes = new ArrayList<String>();
            _itemAttributes.add("xmlns");
            _itemAttributes.add("state");
            _itemAttributes.add("resource");
            _itemAttributes.add("align");
        }

        return super.parseData(data, documentName);
    }

    public List<String> getResourceAttributes() {
        return _resourceAttributes;
    }

    public void setResourceAttributes(List<String> resourceAttributes) {
        _resourceAttributes = resourceAttributes;
    }

    public List<String> getBundleAttributes() {
        return _bundleAttributes;
    }

    public void setBundleAttributes(List<String> bundleAttributes) {
        _bundleAttributes = bundleAttributes;
    }

    @Override
    public boolean processElement(String elementName, Map<String, String> attributeDict) {
        if (super.processElement(elementName, attributeDict)) {
            return true;
        }

        if (elementName.equals("Resources")) {
            MBResourceConfiguration confDef = new MBResourceConfiguration();
            getStack().push(confDef);
            setRootConfig(confDef);
        } else if (elementName.equals("Resource")) {
            checkAttributesForElement(elementName, attributeDict, _resourceAttributes);

            String type = MBConstants.C_STATED_RESOURCE_STATE_TYPE_IMAGE;

            MBResourceDefinition resourceDef = new MBResourceDefinition();
            resourceDef.setResourceId(attributeDict.get("id"));
            resourceDef.setUrl(attributeDict.get("url"));
            resourceDef.setColor(attributeDict.get("color"));
            resourceDef.setCacheable(Boolean.parseBoolean(attributeDict.get("cacheable")));
            resourceDef.setAlign(attributeDict.get("align"));

            if (StringUtil.isNotBlank(resourceDef.getColor())) {
                type = MBConstants.C_STATED_RESOURCE_STATE_TYPE_COLOR;
            }
            resourceDef.setType(type);
            if (attributeDict.containsKey("ttl")) {
                resourceDef.setTtl(Integer.parseInt(attributeDict.get("ttl")));
            }

            notifyProcessed(resourceDef);
        } else if (elementName.equals("Bundle")) {
            checkAttributesForElement(elementName, attributeDict, _bundleAttributes);

            MBBundleDefinition resourceDef = new MBBundleDefinition();

            resourceDef.setUrl(attributeDict.get("url"));
            resourceDef.setLanguageCode(attributeDict.get("languageCode"));

            notifyProcessed(resourceDef);
        } else if (elementName.equals("StatedResource")) {
            checkAttributesForElement(elementName, attributeDict, _statedResourceAttributes);

            MBResourceDefinition resourceDef = new MBResourceDefinition();
            resourceDef.setType("STATEDIMAGE");
            resourceDef.setResourceId(attributeDict.get("id"));
            resourceDef.setViewType(attributeDict.get("viewType"));

            notifyProcessed(resourceDef);
        } else if (elementName.equals("LayeredResource")) {
            checkAttributesForElement(elementName, attributeDict, _layeredResourceAttributes);

            MBResourceDefinition resourceDef = new MBResourceDefinition();
            resourceDef.setType("LAYEREDIMAGE");
            resourceDef.setResourceId(attributeDict.get("id"));

            notifyProcessed(resourceDef);
        } else if (elementName.equals("Item")) {
            checkAttributesForElement(elementName, attributeDict, _itemAttributes);

            MBItemDefinition itemDefinition = new MBItemDefinition();
            itemDefinition.setResource(attributeDict.get("resource"));
            itemDefinition.setState(attributeDict.get("state"));
            itemDefinition.setAlign(attributeDict.get("align"));

            notifyProcessed(itemDefinition);
        } else {
            return false;
        }

        return true;
    }

    @Override
    public void didProcessElement(String elementName) {
        if (!elementName.equals("Resources") && !elementName.equals("Include")) {
            getStack().pop();
        }
    }

    @Override
    public boolean isConcreteElement(String element) {
        return super.isConcreteElement(element) || element.equals("Resource") || element.equals("Bundle") || element.equals("Resources")
                || element.equals("StatedResource") || element.equals("LayeredResource") || element.equals("Item");
    }

    @Override
    public boolean isIgnoredElement(String element) {
        return false;
    }

    public void setStatedResourcesAttributes(List<String> statedResourcesAttributes) {
        _statedResourceAttributes = statedResourcesAttributes;
    }

    public List<String> getStatedResourcesAttributes() {
        return _statedResourceAttributes;
    }

    public void setItemAttributes(List<String> itemAttributes) {
        _itemAttributes = itemAttributes;
    }

    public List<String> getItemAttributes() {
        return _itemAttributes;
    }

    public void setLayeredResourceAttributes(List<String> layeredResourceAttributes) {
        _layeredResourceAttributes = layeredResourceAttributes;
    }

    public List<String> getLayeredResourceAttributes() {
        return _layeredResourceAttributes;
    }

}
