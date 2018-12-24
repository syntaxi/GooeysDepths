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
package org.terasology.depths.caveGen.worldGen.caverSystem;

import org.terasology.math.geom.Vector3f;
import org.terasology.math.geom.Vector3i;
import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise;
import org.terasology.world.generation.Border3D;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Produces;

@Produces(StoneVeinFacet.class)
public class StoneProvider implements FacetProvider {
    private Noise noise;


    @Override
    public void setSeed(long seed) {
        noise = new SubSampledNoise(new SimplexNoise(seed + 17), new Vector3f(0.1f, 0.1f, 0.1f), 1);
    }

    @Override
    public void process(GeneratingRegion region) {
        Border3D border = region.getBorderForFacet(StoneVeinFacet.class);
        StoneVeinFacet facet = new StoneVeinFacet(region.getRegion(), border);
        for (Vector3i position : region.getRegion()) {
            facet.setWorld(position, noise.noise(position.x(), position.y(), position.z()) < -0.5);
        }
        region.setRegionFacet(StoneVeinFacet.class, facet);
    }
}
