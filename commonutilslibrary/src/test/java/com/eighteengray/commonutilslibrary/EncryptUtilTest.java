package com.eighteengray.commonutilslibrary;

import org.junit.Test;
import static org.junit.Assert.*;



/**
 * Created by lutao on 2017/2/8.
 */
public class EncryptUtilTest
{
    String md5Text = "helloworld";
    String md5EncyptText = "fc5e038d38a57032085441e7fe7010b0";

    @Test
    public void encryptMd5() throws Exception
    {
        assertEquals(md5EncyptText, EncryptUtil.encryptMd5(md5Text));
    }


    @Test
    public void decryptMd5() throws Exception
    {
        assertEquals(md5Text, EncryptUtil.convertMD5(md5EncyptText));
        assertEquals(md5Text, EncryptUtil.decryptMD5(md5EncyptText));
    }


    @Test
    public void encryptDES() throws Exception
    {

    }

    @Test
    public void encryptBase64() throws Exception
    {

    }

    @Test
    public void decryptBase64() throws Exception
    {

    }

}