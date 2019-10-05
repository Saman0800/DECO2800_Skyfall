
    @Test
    public void TestHarvest() {
        Tile tile1 = w.getTile(0.0f, 0.0f);

        ForestTree tree1 = new ForestTree(tile1, true);

        List<AbstractEntity> drops = tree1.harvest(tile1);

        assertTrue("Unexpected drop size.", 0 <= drops.size());
        assertTrue("Unexpected drop size.", 15 >= drops.size());

        // Check that the drops are instance of Woodblocks
        if (0 < drops.size()) {
            AbstractEntity dropItem = drops.get(0);
            assertTrue("Incorrect instance type for tree drop", dropItem instanceof WoodCube);
        }
    }

    @Test
    public void TestHashCode() {
        Tile tile1 = w.getTile(0.0f, 0.0f);

        ForestTree tree1 = new ForestTree(tile1, true);

        // Calculate the expected hash code
        float result = 1;
        final float prime = 31;
        result = (result + tree1.getCol()) * prime;
        result = (result + tree1.getRow()) * prime;
        result = (result + tree1.getHeight()) * prime;

        assertEquals("Unexpected hashcode for tree.", tree1.hashCode(), result, 0.0);
    }

    @Test
    public void TestWoodAmount() {
        Tile tile1 = w.getTile(0.0f, 0.0f);
        Tile tile2 = w.getTile(0.0f, 0.5f);

        Map<HexVector, String> texture = new HashMap<>();
        texture.put(new HexVector(0.0f, 0.0f), "tree");

        ForestTree tree1 = new ForestTree(tile1, true);
        ForestTree tree2 = new ForestTree(0.0f, 0.5f, 2, texture);

        assertEquals(15, tree1.getWoodAmount());

        assertEquals(15, tree2.getWoodAmount());
        tree2.decreaseWoodAmount();
        assertEquals(14, tree2.getWoodAmount());
    }
}