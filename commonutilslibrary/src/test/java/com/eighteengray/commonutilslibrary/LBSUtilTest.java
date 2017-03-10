package com.eighteengray.commonutilslibrary;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lutao on 2017/2/7.
 */
public class LBSUtilTest
{


    @Test
    public void getDistance() throws Exception
    {
        assertEquals(123.2, 1234.5, LBSUtil.GetDistance(0, 10, 0, 20));
    }

}