package gu.common;

import java.io.UnsupportedEncodingException;

public class utiletc {
    
    /**
     * 한글 문자열 자르기 (VO에서 호출): 출처는 오래되서 ...
     */
    public static String getShortString(String str, Integer len) {
        try {
            if (str.getBytes("UTF-8").length > len) {
                str = strCut(str, len) + "...";
            }
        } catch (UnsupportedEncodingException ex) {
            System.out.println("UnsupportedEncodingException: getShortString()");
        }

        return str;
    }
    
    /**
     * 한글 문자열 자르기 (실제 처리): 출처는 오래되서 ...
     */
    private static String strCut(String szText, int nlength) {
      String rval = szText;
      int oooF = 0;
      int oooL = 0;
      int rrrF = 0;
      int rrrL = 0;
      int nlengthPrev = 0;
      try {
          byte[] bytes = rval.getBytes("UTF-8"); // 바이트로 보관
          // x부터 y길이만큼 잘라낸다. 한글안깨지게.
          int jcount = 0;
          if (nlengthPrev > 0) {
              while (jcount < bytes.length) {
                  if ((bytes[jcount] & 0x80) != 0) {
                      oooF += 2;
                      rrrF += 3;
                      if (oooF + 2 > nlengthPrev) {
                          break;
                      }
                      jcount += 3;
                  } else {
                      if (oooF + 1 > nlengthPrev) {
                          break;
                      }
                      ++oooF;
                      ++rrrF;
                      ++jcount;
                  }
              }
          }
          jcount = rrrF;
          while (jcount < bytes.length) {
              if ((bytes[jcount] & 0x80) != 0) {
                  if (oooL + 2 > nlength) {
                      break;
                  }
                  oooL += 2;
                  rrrL += 3;
                  jcount += 3;
              } else {
                  if (oooL + 1 > nlength) {
                      break;
                  }
                  ++oooL;
                  ++rrrL;
                  ++jcount;
              }
          }
          rval = new String(bytes, rrrF, rrrL, "UTF-8"); // charset 옵션
      } catch (UnsupportedEncodingException ex) {
          System.out.println("UnsupportedEncodingException: strCut()");
      }
      return rval;
     }
}
