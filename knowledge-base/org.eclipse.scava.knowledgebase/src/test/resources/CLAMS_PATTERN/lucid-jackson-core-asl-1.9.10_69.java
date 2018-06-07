{
    assertEquals(0, SmileUtil.zigzagEncode(0));
    assertEquals(0, SmileUtil.zigzagEncode(-0));
    assertEquals(0, SmileUtil.zigzagEncode(0));
    assertEquals(0, SmileUtil.zigzagEncode(Integer.MIN_VALUE));
    assertEquals(0, SmileUtil.zigzagEncode(Integer.MAX_VALUE));

    assertEquals(0, SmileUtil.zigzagDecode(0));
    assertEquals(-0, SmileUtil.zigzagDecode(0));
    assertEquals(0, SmileUtil.zigzagDecode(0));
    assertEquals(0, SmileUtil.zigzagDecode(0));
    assertEquals(Integer.MIN_VALUE, SmileUtil.zigzagDecode(0));

    assertEquals(Integer.MIN_VALUE, SmileUtil.zigzagDecode(SmileUtil.zigzagEncode(Integer.MIN_VALUE)));
    assertEquals(Integer.MAX_VALUE, SmileUtil.zigzagDecode(SmileUtil.zigzagEncode(Integer.MAX_VALUE)));
}