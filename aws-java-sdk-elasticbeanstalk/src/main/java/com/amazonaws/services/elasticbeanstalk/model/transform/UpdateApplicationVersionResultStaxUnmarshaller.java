/*
 * Copyright 2010-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 * 
 *  http://aws.amazon.com/apache2.0
 * 
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.amazonaws.services.elasticbeanstalk.model.transform;

import java.util.Map;
import java.util.Map.Entry;

import javax.xml.stream.events.XMLEvent;

import com.amazonaws.services.elasticbeanstalk.model.*;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.transform.MapEntry;
import com.amazonaws.transform.StaxUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeStaxUnmarshallers.*;

/**
 * Update Application Version Result StAX Unmarshaller
 */
public class UpdateApplicationVersionResultStaxUnmarshaller implements Unmarshaller<UpdateApplicationVersionResult, StaxUnmarshallerContext> {

    public UpdateApplicationVersionResult unmarshall(StaxUnmarshallerContext context) throws Exception {
        UpdateApplicationVersionResult updateApplicationVersionResult = new UpdateApplicationVersionResult();
        int originalDepth = context.getCurrentDepth();
        int targetDepth = originalDepth + 1;

        if (context.isStartOfDocument()) targetDepth += 2;

        while (true) {
            XMLEvent xmlEvent = context.nextEvent();
            if (xmlEvent.isEndDocument()) return updateApplicationVersionResult;

            if (xmlEvent.isAttribute() || xmlEvent.isStartElement()) {
                if (context.testExpression("ApplicationVersion", targetDepth)) {
                    updateApplicationVersionResult.setApplicationVersion(ApplicationVersionDescriptionStaxUnmarshaller.getInstance().unmarshall(context));
                    continue;
                }
            } else if (xmlEvent.isEndElement()) {
                if (context.getCurrentDepth() < originalDepth) {
                    return updateApplicationVersionResult;
                }
            }
        }
    }

    private static UpdateApplicationVersionResultStaxUnmarshaller instance;
    public static UpdateApplicationVersionResultStaxUnmarshaller getInstance() {
        if (instance == null) instance = new UpdateApplicationVersionResultStaxUnmarshaller();
        return instance;
    }
}
    