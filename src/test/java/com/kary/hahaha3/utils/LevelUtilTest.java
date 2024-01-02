package com.kary.hahaha3.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LevelUtilTest {
    private LevelUtil levelUtil;
    @Test
    public void testLevel_1(){
        assertEquals("王者",levelUtil.getLevel(1800));
    }
    @Test
    public void testLevel_2(){
        assertEquals("钻石",levelUtil.getLevel(1500));
    }
    @Test
    public void testLevel_rest(){
        String[] levels=new String[]{"","青铜","白银","黄金","白金","钻石","王者"};
        assertEquals(levels[Math.max(1499,0)/300+1],levelUtil.getLevel(1499));
        assertEquals(levels[Math.max(1199,0)/300+1],levelUtil.getLevel(1199));
        assertEquals(levels[Math.max(899,0)/300+1],levelUtil.getLevel(899));
        assertEquals(levels[Math.max(599,0)/300+1],levelUtil.getLevel(599));
    }
}
