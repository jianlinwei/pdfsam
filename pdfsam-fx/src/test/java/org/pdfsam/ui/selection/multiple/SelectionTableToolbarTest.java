/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 09/ago/2014
 * Copyright 2017 by Sober Lemur S.a.s. (info@pdfsam.org).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pdfsam.ui.selection.multiple;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.pdfsam.ui.selection.multiple.SelectionChangedEvent.select;
import static org.sejda.eventstudio.StaticStudio.eventStudio;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.categories.TestFX;
import org.loadui.testfx.utils.FXTestUtils;
import org.mockito.ArgumentCaptor;
import org.pdfsam.test.ClearEventStudioRule;
import org.pdfsam.test.HitTestListener;
import org.pdfsam.ui.commons.ClearModuleEvent;
import org.pdfsam.ui.commons.RemoveSelectedEvent;
import org.pdfsam.ui.selection.multiple.SelectionTableToolbar.ClearButton;
import org.pdfsam.ui.selection.multiple.SelectionTableToolbar.MoveDownButton;
import org.pdfsam.ui.selection.multiple.SelectionTableToolbar.MoveUpButton;
import org.pdfsam.ui.selection.multiple.SelectionTableToolbar.RemoveButton;
import org.pdfsam.ui.selection.multiple.move.MoveSelectedEvent;
import org.sejda.eventstudio.Listener;

import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * @author Andrea Vacondio
 *
 */
@Category(TestFX.class)
public class SelectionTableToolbarTest extends GuiTest {

    private static final String MODULE = "MODULE";
    @Rule
    public ClearEventStudioRule clearStudio = new ClearEventStudioRule(MODULE);

    @Override
    protected Parent getRootNode() {
        SelectionTableToolbar victim = new SelectionTableToolbar(MODULE, true);
        victim.setId("victim");
        return victim;
    }

    @Test
    public void clear() {
        HitTestListener<ClearModuleEvent> listener = new HitTestListener<>();
        eventStudio().add(ClearModuleEvent.class, listener, MODULE);
        click(b -> b instanceof ClearButton);
        assertTrue(listener.isHit());
    }

    @Test
    public void clearAllSettings() {
        Listener<ClearModuleEvent> listener = mock(Listener.class);
        ArgumentCaptor<ClearModuleEvent> captor = ArgumentCaptor.forClass(ClearModuleEvent.class);
        eventStudio().add(ClearModuleEvent.class, listener, MODULE);
        click(".arrow-button").click(".menu-item");
        verify(listener).onEvent(captor.capture());
        assertTrue(captor.getValue().clearEverything);
    }

    @Test
    public void add() {
        // TODO don't know how to test file choosers
    }

    @Test
    public void remove() throws Exception {
        HitTestListener<RemoveSelectedEvent> listener = new HitTestListener<>();
        eventStudio().add(RemoveSelectedEvent.class, listener, MODULE);
        Node victim = find(b -> b instanceof RemoveButton);
        enableByFiringSelectionChange(victim);
        click(victim);
        assertTrue(listener.isHit());
    }

    @Test
    public void moveUp() throws Exception {
        HitTestListener<MoveSelectedEvent> listener = new HitTestListener<>();
        eventStudio().add(MoveSelectedEvent.class, listener, MODULE);
        Node victim = find(b -> b instanceof MoveUpButton);
        enableByFiringSelectionChange(victim);
        click(victim);
        assertTrue(listener.isHit());
    }

    @Test
    public void moveDown() throws Exception {
        HitTestListener<MoveSelectedEvent> listener = new HitTestListener<>();
        eventStudio().add(MoveSelectedEvent.class, listener, MODULE);
        Node victim = find(b -> b instanceof MoveDownButton);
        enableByFiringSelectionChange(victim);
        click(victim);
        assertTrue(listener.isHit());
    }

    private void enableByFiringSelectionChange(Node victim) throws Exception {
        assertTrue(victim.isDisabled());
        FXTestUtils.invokeAndWait(() -> eventStudio().broadcast(select(asList(2, 3)).ofTotalRows(5), MODULE), 1);
        assertFalse(victim.isDisabled());
    }
}
