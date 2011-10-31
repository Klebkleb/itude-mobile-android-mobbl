package com.itude.mobile.mobbl2.client.core.services.datamanager.handlers;

import com.itude.mobile.mobbl2.client.core.configuration.webservices.MBEndPointDefinition;
import com.itude.mobile.mobbl2.client.core.configuration.webservices.MBWebservicesConfiguration;
import com.itude.mobile.mobbl2.client.core.configuration.webservices.MBWebservicesConfigurationParser;
import com.itude.mobile.mobbl2.client.core.model.MBDocument;
import com.itude.mobile.mobbl2.client.core.services.MBMetadataService;
import com.itude.mobile.mobbl2.client.core.services.datamanager.MBDataHandlerBase;
import com.itude.mobile.mobbl2.client.core.util.DataUtil;
import com.itude.mobile.mobbl2.client.core.util.MBCacheManager;

public abstract class MBWebserviceDataHandler extends MBDataHandlerBase
{
  private final MBWebservicesConfiguration _webServiceConfiguration;

  public MBWebserviceDataHandler()
  {
    MBWebservicesConfigurationParser parser = new MBWebservicesConfigurationParser();
    String documentName = MBMetadataService.getEndpointsName();
    byte[] data = DataUtil.getInstance().readFromAssetOrFile(documentName);
    _webServiceConfiguration = (MBWebservicesConfiguration) parser.parseData(data, documentName);
  }
  
  @Override
  public MBDocument loadDocument(String documentName)
  {
    return loadDocument(documentName, null);
  }

  @Override
  public MBDocument loadDocument(String documentName, MBDocument doc)
  {
    MBEndPointDefinition endPoint = getEndPointForDocument(documentName);
    boolean cacheable =  endPoint.getCacheable();

    if (cacheable)
    {
      MBDocument result;
      if (doc == null)
      {
        result = MBCacheManager.documentForKey(documentName);
      }
      else
      {
        result = MBCacheManager.documentForKey(documentName + doc.getUniqueId());
      }

      if (result != null)
      {
        return result;
      }
    }
    
    MBDocument result = doLoadDocument(documentName, doc);
    
    if (cacheable)
    {
      if (doc == null)
      {
        MBCacheManager.setDocument(result, documentName, endPoint.getTtl());
      }
      else
      {
        MBCacheManager.setDocument(result, documentName + doc.getUniqueId(), endPoint.getTtl());
      }
    }
    
    return result;
  }
  
  protected abstract MBDocument doLoadDocument(String documentName, MBDocument doc);
  
  @Override
  public void storeDocument(MBDocument document)
  {
  }

  public MBEndPointDefinition getEndPointForDocument(String name)
  {
    return _webServiceConfiguration.getEndPointForDocumentName(name);
  }

}
