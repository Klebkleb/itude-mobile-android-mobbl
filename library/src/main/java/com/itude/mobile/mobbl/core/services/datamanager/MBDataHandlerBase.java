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
package com.itude.mobile.mobbl.core.services.datamanager;

import com.itude.mobile.android.util.log.MBLog;
import com.itude.mobile.mobbl.core.configuration.endpoints.MBEndPointDefinition;
import com.itude.mobile.mobbl.core.model.MBDocument;
import com.itude.mobile.mobbl.core.services.operation.MBDocumentOperation;
import com.itude.mobile.mobbl.core.util.MBConstants;

/**
 * Base data handler
 */
public class MBDataHandlerBase implements MBDataHandler {

    @Override
    public MBDocument loadDocument(String documentName) {
        MBLog.w(MBConstants.APPLICATION_NAME, "MBDataHandlerBase: No loadDocument implementation for " + documentName);
        return null;
    }

    @Override
    public MBDocument loadFreshDocument(String documentName) {
        MBLog.w(MBConstants.APPLICATION_NAME, "MBDataHandlerBase: No loadFreshDocument implementation for " + documentName);
        return null;
    }

    /**
     * Load document with specified name using a specific parser eg. JSON or XML.
     * Use static variables from MBDocumentFactory to specify the parser.
     *
     * @param documentName documentName
     * @param parser       parser
     * @return {@link com.itude.mobile.mobbl.core.model.MBDocument}
     */
    @Override
    public MBDocument loadDocument(String documentName, String parser) {
        return loadDocument(documentName);
    }

    @Override
    public MBDocument loadFreshDocument(String documentName, String parser) {
        return loadFreshDocument(documentName);
    }

    @Override
    public MBDocument loadDocument(String documentName, MBDocument args, MBEndPointDefinition endPoint) {
        MBLog.w(MBConstants.APPLICATION_NAME, "MBDataHandlerBase: No loadDocument implementation for " + documentName);
        return null;
    }

    @Override
    public MBDocument loadFreshDocument(String documentName, MBDocument args, MBEndPointDefinition endPoint) {
        MBLog.w(MBConstants.APPLICATION_NAME, "MBDataHandlerBase: No loadFreshDocument implementation for " + documentName);
        return null;
    }

    @Override
    public MBDocument loadDocument(String documentName, MBDocument args, String parser, MBEndPointDefinition endPoint) {
        return loadDocument(documentName, args, endPoint);
    }

    @Override
    public MBDocument loadFreshDocument(String documentName, MBDocument args, String parser, MBEndPointDefinition endPoint) {
        return loadFreshDocument(documentName, args, endPoint);
    }

    @Override
    public void storeDocument(MBDocument document) {
        MBLog.w(MBConstants.APPLICATION_NAME, "MBDataHandlerBase: No storeDocument implementation for " + document.getDefinition().getName());
    }

    @Override
    public MBDocument loadDocument(String documentName, MBDocument args, MBEndPointDefinition endPointDefenition, String documentParser) {
        MBDocument result = null;
        if (args != null) {
            result = loadDocument(documentName, args, documentParser, endPointDefenition);
        } else {
            result = loadDocument(documentName, documentParser);
        }
        return result;
    }

    @Override
    public MBDocument loadFreshDocument(String documentName, MBDocument args, MBEndPointDefinition endPointDefenition, String documentParser) {
        MBDocument result = null;
        if (args != null) {
            result = loadFreshDocument(documentName, args, documentParser, endPointDefenition);
        } else {
            result = loadFreshDocument(documentName, documentParser);
        }
        return result;
    }

    @Override
    public MBDocumentOperation createLoadDocumentOperation(String documentName, MBDocument arguments) {
        return new MBDocumentOperation(this, documentName, arguments);
    }

    @Override
    public MBDocumentOperation createStoreDocumentOperation(MBDocument document) {
        return new MBDocumentOperation(this, document);
    }

}
