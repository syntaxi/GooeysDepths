/*
 * Copyright 2018 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.depths.caveGen.worldGen.flora;

import org.terasology.depths.caveGen.worldGen.caverSystem.CaveSystemFacet;
import org.terasology.math.Region3i;
import org.terasology.math.geom.Vector3i;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetBorder;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Updates;


@Updates(@Facet(value = CaveSystemFacet.class, border = @FacetBorder(top = 1)))
public class GrassProvider implements FacetProvider {
    @Override
    public void process(GeneratingRegion region) {
        CaveSystemFacet caveFacet = region.getRegionFacet(CaveSystemFacet.class);

        Region3i processRegion = region.getRegion();
        Vector3i up = new Vector3i();
        for (Vector3i position : processRegion) {
            byte density = caveFacet.getWorld(position);
            if (density == CaveSystemFacet.DIRT) {
                up.set(position);
                up.addY(1);
                caveFacet.setWorld(position, caveFacet.getWorld(up) == 0 ? CaveSystemFacet.GRASS : density);
            }
        }
    }
}
