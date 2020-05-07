package tests.maptests.prim_object;

import gnu.trove.TIntObjectHashMap;
import tests.maptests.IMapTest;
import tests.maptests.ITestSet;

/**
 * Trove TIntObjectHashMap
 */
public class TroveJbIntObjectMapTest implements ITestSet
{
    @Override
    public IMapTest getTest() {
        return new TroveIntObjectGetTest();
    }

    @Override
    public IMapTest putTest() {
        return new TroveIntObjectPutTest();
    }

    @Override
    public IMapTest removeTest() {
        return new TroveIntObjectRemoveTest();
    }

    private static class TroveIntObjectGetTest extends AbstractPrimObjectGetTest {
        private TIntObjectHashMap<Integer> m_map;
        @Override
        public void setup(int[] keys, float fillFactor, int oneFailOutOf) {
            super.setup(keys, fillFactor, oneFailOutOf);
            m_map = new TIntObjectHashMap<>( keys.length, fillFactor );
            for ( final int key : keys ) m_map.put( key % oneFailOutOf == 0 ? key + 1 : key, key );
        }

        @Override
        public int test() {
            int res = 0;
            for ( int i = 0; i < m_keys.length; ++i )
                if ( m_map.get( m_keys[ i ] ) != null ) res ^= 1;
            return res;
        }
    }

    private static class TroveIntObjectPutTest extends AbstractPrimObjectPutTest {
        @Override
        public int test() {
            final TIntObjectHashMap<Integer> m_map = new TIntObjectHashMap<>( m_keys.length, m_fillFactor );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], null );
            for ( int i = 0; i < m_keys.length; ++i )
                m_map.put( m_keys[ i ], null );
            return m_map.size();
        }
    }

    private static class TroveIntObjectRemoveTest extends AbstractPrimObjectPutTest {
        @Override
        public int test() {
            final TIntObjectHashMap<Integer> m_map = new TIntObjectHashMap<>( m_keys.length / 2 + 1, m_fillFactor );
            final Integer value = 1;
            int add = 0, remove = 0;
            while ( add < m_keys.length )
            {
                m_map.put( m_keys[ add ], value );
                ++add;
                m_map.put( m_keys[ add ], value );
                ++add;
                m_map.remove( m_keys[ remove++ ] );
            }
            return m_map.size();
        }
    }
}

