/*
* Copyright [2016] [George Papadakis (gpapadis@yahoo.gr)]
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package DebugCode;

import BlockBuilding.IBlockBuilding;
import BlockProcessing.AbstractDuplicatePropagation;
import BlockProcessing.UnilateralDuplicatePropagation;
import DataModel.AbstractBlock;
import DataModel.EntityProfile;
import DataReader.EntityReader.IEntityReader;
import DataReader.EntityReader.SerializationReader;
import DataReader.GroundTruthReader.GtSerializationReader;
import DataReader.GroundTruthReader.IGroundTruthReader;
import Utilities.Enumerations.BlockBuildingMethod;
import Utilities.BlockStatistics;
import java.util.List;

/**
 *
 * @author G.A.P. II
 */

public class TestBlockBuildingMethods {
    public static void main(String[] args) {
        String entitiesFilePath = "E:\\Data\\profiles\\10Kprofiles";
        String groundTruthFilePath = "E:\\Data\\groundtruth\\10KIdDuplicates";
        
        IGroundTruthReader gtReader = new GtSerializationReader(groundTruthFilePath);
        final AbstractDuplicatePropagation duplicatePropagation = new UnilateralDuplicatePropagation(gtReader.getDuplicatePairs());
        System.out.println("Existing Duplicates\t:\t" + duplicatePropagation.getDuplicates().size());

        IEntityReader eReader = new SerializationReader(entitiesFilePath);
        List<EntityProfile> profiles = eReader.getEntityProfiles();
        System.out.println("Input Entity Profiles\t:\t" + profiles.size());
        
        for (BlockBuildingMethod blbuMethod : BlockBuildingMethod.values()) {
            System.out.println("\n\nCurrent blocking metohd\t:\t" + blbuMethod);
            IBlockBuilding blockBuildingMethod = BlockBuildingMethod.getDefaultConfiguration(blbuMethod);
            
            System.out.println("Block Building...");
            List<AbstractBlock> blocks = blockBuildingMethod.getBlocks(profiles, null);
            BlockStatistics blStats = new BlockStatistics(blocks, duplicatePropagation);
            blStats.applyProcessing();
        }
    }
}