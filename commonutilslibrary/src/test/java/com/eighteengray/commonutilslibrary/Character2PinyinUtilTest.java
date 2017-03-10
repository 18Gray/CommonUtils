package com.eighteengray.commonutilslibrary;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by lutao on 2017/2/8.
 */
public class Character2PinyinUtilTest
{
    Character2PinyinUtil character2PinyinUtil;

    @Before
    public void setUp() throws Exception
    {
        character2PinyinUtil = Character2PinyinUtil.getInstance();
    }

    @Test
    public void character2Pinyin() throws Exception
    {
        assertEquals("wo", character2PinyinUtil.character2Pinyin("我"));
        assertEquals("wode", character2PinyinUtil.character2Pinyin("我的"));
    }

}