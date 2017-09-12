/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 18/feb/2014
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
package org.pdfsam.ui.io;

import java.io.File;

import org.sejda.eventstudio.annotation.EventListener;

/**
 * Base class for a chooser that remembers the latest working directory
 * 
 * @author Andrea Vacondio
 *
 */
public abstract class BaseRememberingLatestChooser {

    abstract void setInitialDirectory(File value);

    @EventListener
    public void doSetInitialDir(SetLatestDirectoryEvent event) {
        setInitialDirectory(event.getLatest());
    }

}
