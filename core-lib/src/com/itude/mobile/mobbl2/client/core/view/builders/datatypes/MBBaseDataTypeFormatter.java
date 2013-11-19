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
package com.itude.mobile.mobbl2.client.core.view.builders.datatypes;

import java.util.Locale;

import com.itude.mobile.mobbl2.client.core.services.MBLocalizationService;
import com.itude.mobile.mobbl2.client.core.view.MBField;

public abstract class MBBaseDataTypeFormatter implements MBDataTypeFormatter
{

  protected Locale getLocale()
  {
    return MBLocalizationService.getInstance().getLocale();
  }

  @Override
  public String format(MBField field)
  {
    try
    {
      String value = field.getValue();
      if (value == null) return null;
      boolean fieldValueSameAsNilValue = value.equals(field.getValueIfNil());

      if (!value.equals(fieldValueSameAsNilValue)) return actuallyFormat(value);
      else return value;

    }
    catch (NumberFormatException nfe)
    {
      throw new NumberFormatException("Unable to format value for field: " + field.toString());
    }

  }

  protected abstract String actuallyFormat(String value);

}
