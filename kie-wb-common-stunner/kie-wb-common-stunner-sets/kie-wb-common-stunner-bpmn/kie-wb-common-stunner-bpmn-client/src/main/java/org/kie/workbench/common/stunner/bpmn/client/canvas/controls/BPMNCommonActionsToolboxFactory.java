/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.bpmn.client.canvas.controls;

import java.util.Collection;

import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.jboss.errai.ioc.client.api.ManagedInstance;
import org.kie.workbench.common.stunner.bpmn.client.canvas.controls.util.ActionsToolboxHelper;
import org.kie.workbench.common.stunner.bpmn.qualifiers.BPMN;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.components.toolbox.actions.AbstractActionsToolboxFactory;
import org.kie.workbench.common.stunner.core.client.components.toolbox.actions.ActionsToolboxView;
import org.kie.workbench.common.stunner.core.client.components.toolbox.actions.CommonActionsToolbox;
import org.kie.workbench.common.stunner.core.client.components.toolbox.actions.ToolboxAction;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.forms.client.components.toolbox.FormGenerationToolboxAction;

/**
 * Produces same toolbox content and view as the @CommonActionsToolbox but
 * it additionally add the form generation action, if it applies.
 */
@Dependent
@BPMN
public class BPMNCommonActionsToolboxFactory extends AbstractActionsToolboxFactory {

    private final ManagedInstance<FormGenerationToolboxAction> generateFormsActions;
    private final ManagedInstance<ActionsToolboxView> views;
    private final ActionsToolboxHelper actionsToolboxHelper;

    protected BPMNCommonActionsToolboxFactory() {
        this.generateFormsActions = null;
        this.views = null;
        this.actionsToolboxHelper = null;
    }

    @Inject
    public BPMNCommonActionsToolboxFactory(final @Any ManagedInstance<FormGenerationToolboxAction> generateFormsActions,
                                           final @Any @CommonActionsToolbox ManagedInstance<ActionsToolboxView> views,
                                           final ActionsToolboxHelper actionsToolboxHelper) {
        this.generateFormsActions = generateFormsActions;
        this.views = views;
        this.actionsToolboxHelper = actionsToolboxHelper;
    }

    @Override
    protected ActionsToolboxView<?> newViewInstance() {
        return views.get();
    }

    @Override
    public Collection<ToolboxAction<AbstractCanvasHandler>> getActions(final AbstractCanvasHandler canvasHandler,
                                                                       final Element<?> e) {
        return actionsToolboxHelper.getActions(canvasHandler, e);
    }

    @PreDestroy
    public void destroy() {
        generateFormsActions.destroyAll();
        views.destroyAll();
    }
}
