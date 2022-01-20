/**
 * Copyright (C) 2020 Mike Hummel (mh@mhus.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.mhus.con.plugin;

import java.io.File;

import de.mhus.con.api.AMojo;
import de.mhus.con.api.ConUtil;
import de.mhus.con.api.Context;
import de.mhus.con.api.MojoException;
import de.mhus.lib.core.MString;

@AMojo(name = "maven")
public class MavenMojo extends AbstractMavenExecute {

    @Override
    public boolean execute2(File dir, String moduleName, Context context) throws Exception {
        String mvnPath = ConUtil.cmdLocation(context.getConductor(), "mvn");
        String cmd = mvnPath + " " + MString.join(context.getStep().getArguments(), " ");
        String[] res = ConUtil.execute(
                context.getConductor(),
                getCmdName(context, moduleName), 
                dir, 
                cmd, 
                true);
        if (!res[2].equals("0")
                && !context.getProperties()
                        .getBoolean(ConUtil.PROPERTY_STEP_IGNORE_RETURN_CODE, false))
            throw new MojoException(context, "not successful", cmd, res[1], res[2]);
        return true;
    }
}
