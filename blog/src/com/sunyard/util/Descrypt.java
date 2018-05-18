package com.sunyard.util;

import java.nio.ByteBuffer;

public class Descrypt {
  private int KnL[] = new int[32];
  private static char Df_Key[] = {
      0x01, 0x23, 0x45, 0x67, 0x89, 0xab, 0xcd, 0xef,
      0xfe, 0xdc, 0xba, 0x98, 0x76, 0x54, 0x32, 0x10,
      0x89, 0xab, 0xcd, 0xef, 0x01, 0x23, 0x45, 0x67};

  private static short bytebit[] = {
      0200, 0100, 040, 020, 010, 04, 02, 01};
  private static int bigbyte[] = {
      0x800000, 0x400000, 0x200000, 0x100000,
      0x80000, 0x40000, 0x20000, 0x10000,
      0x8000, 0x4000, 0x2000, 0x1000,
      0x800, 0x400, 0x200, 0x100,
      0x80, 0x40, 0x20, 0x10,
      0x8, 0x4, 0x2, 0x1};

  private static char pc1[] = {
      56, 48, 40, 32, 24, 16, 8, 0, 57, 49, 41, 33, 25, 17,
      9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35,
      62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21,
      13, 5, 60, 52, 44, 36, 28, 20, 12, 4, 27, 19, 11, 3};

  private static char totrot[] = {
      1, 2, 4, 6, 8, 10, 12, 14, 15, 17, 19, 21, 23, 25, 27, 28};
  private static char pc2[] = {
      13, 16, 10, 23, 0, 4, 2, 27, 14, 5, 20, 9,
      22, 18, 11, 3, 25, 7, 15, 6, 26, 19, 12, 1,
      40, 51, 30, 36, 46, 54, 29, 39, 50, 44, 32, 47,
      43, 48, 38, 55, 33, 52, 45, 41, 49, 35, 28, 31};

  private static int SP1[] = {
      0x01010400, 0x00000000, 0x00010000, 0x01010404,
      0x01010004, 0x00010404, 0x00000004, 0x00010000,
      0x00000400, 0x01010400, 0x01010404, 0x00000400,
      0x01000404, 0x01010004, 0x01000000, 0x00000004,
      0x00000404, 0x01000400, 0x01000400, 0x00010400,
      0x00010400, 0x01010000, 0x01010000, 0x01000404,
      0x00010004, 0x01000004, 0x01000004, 0x00010004,
      0x00000000, 0x00000404, 0x00010404, 0x01000000,
      0x00010000, 0x01010404, 0x00000004, 0x01010000,
      0x01010400, 0x01000000, 0x01000000, 0x00000400,
      0x01010004, 0x00010000, 0x00010400, 0x01000004,
      0x00000400, 0x00000004, 0x01000404, 0x00010404,
      0x01010404, 0x00010004, 0x01010000, 0x01000404,
      0x01000004, 0x00000404, 0x00010404, 0x01010400,
      0x00000404, 0x01000400, 0x01000400, 0x00000000,
      0x00010004, 0x00010400, 0x00000000, 0x01010004};

  private static int SP2[] = {
      0x80108020, 0x80008000, 0x00008000, 0x00108020,
      0x00100000, 0x00000020, 0x80100020, 0x80008020,
      0x80000020, 0x80108020, 0x80108000, 0x80000000,
      0x80008000, 0x00100000, 0x00000020, 0x80100020,
      0x00108000, 0x00100020, 0x80008020, 0x00000000,
      0x80000000, 0x00008000, 0x00108020, 0x80100000,
      0x00100020, 0x80000020, 0x00000000, 0x00108000,
      0x00008020, 0x80108000, 0x80100000, 0x00008020,
      0x00000000, 0x00108020, 0x80100020, 0x00100000,
      0x80008020, 0x80100000, 0x80108000, 0x00008000,
      0x80100000, 0x80008000, 0x00000020, 0x80108020,
      0x00108020, 0x00000020, 0x00008000, 0x80000000,
      0x00008020, 0x80108000, 0x00100000, 0x80000020,
      0x00100020, 0x80008020, 0x80000020, 0x00100020,
      0x00108000, 0x00000000, 0x80008000, 0x00008020,
      0x80000000, 0x80100020, 0x80108020, 0x00108000};

  private static int SP3[] = {
      0x00000208, 0x08020200, 0x00000000, 0x08020008,
      0x08000200, 0x00000000, 0x00020208, 0x08000200,
      0x00020008, 0x08000008, 0x08000008, 0x00020000,
      0x08020208, 0x00020008, 0x08020000, 0x00000208,
      0x08000000, 0x00000008, 0x08020200, 0x00000200,
      0x00020200, 0x08020000, 0x08020008, 0x00020208,
      0x08000208, 0x00020200, 0x00020000, 0x08000208,
      0x00000008, 0x08020208, 0x00000200, 0x08000000,
      0x08020200, 0x08000000, 0x00020008, 0x00000208,
      0x00020000, 0x08020200, 0x08000200, 0x00000000,
      0x00000200, 0x00020008, 0x08020208, 0x08000200,
      0x08000008, 0x00000200, 0x00000000, 0x08020008,
      0x08000208, 0x00020000, 0x08000000, 0x08020208,
      0x00000008, 0x00020208, 0x00020200, 0x08000008,
      0x08020000, 0x08000208, 0x00000208, 0x08020000,
      0x00020208, 0x00000008, 0x08020008, 0x00020200};

  private static int SP4[] = {
      0x00802001, 0x00002081, 0x00002081, 0x00000080,
      0x00802080, 0x00800081, 0x00800001, 0x00002001,
      0x00000000, 0x00802000, 0x00802000, 0x00802081,
      0x00000081, 0x00000000, 0x00800080, 0x00800001,
      0x00000001, 0x00002000, 0x00800000, 0x00802001,
      0x00000080, 0x00800000, 0x00002001, 0x00002080,
      0x00800081, 0x00000001, 0x00002080, 0x00800080,
      0x00002000, 0x00802080, 0x00802081, 0x00000081,
      0x00800080, 0x00800001, 0x00802000, 0x00802081,
      0x00000081, 0x00000000, 0x00000000, 0x00802000,
      0x00002080, 0x00800080, 0x00800081, 0x00000001,
      0x00802001, 0x00002081, 0x00002081, 0x00000080,
      0x00802081, 0x00000081, 0x00000001, 0x00002000,
      0x00800001, 0x00002001, 0x00802080, 0x00800081,
      0x00002001, 0x00002080, 0x00800000, 0x00802001,
      0x00000080, 0x00800000, 0x00002000, 0x00802080};

  private static int SP5[] = {
      0x00000100, 0x02080100, 0x02080000, 0x42000100,
      0x00080000, 0x00000100, 0x40000000, 0x02080000,
      0x40080100, 0x00080000, 0x02000100, 0x40080100,
      0x42000100, 0x42080000, 0x00080100, 0x40000000,
      0x02000000, 0x40080000, 0x40080000, 0x00000000,
      0x40000100, 0x42080100, 0x42080100, 0x02000100,
      0x42080000, 0x40000100, 0x00000000, 0x42000000,
      0x02080100, 0x02000000, 0x42000000, 0x00080100,
      0x00080000, 0x42000100, 0x00000100, 0x02000000,
      0x40000000, 0x02080000, 0x42000100, 0x40080100,
      0x02000100, 0x40000000, 0x42080000, 0x02080100,
      0x40080100, 0x00000100, 0x02000000, 0x42080000,
      0x42080100, 0x00080100, 0x42000000, 0x42080100,
      0x02080000, 0x00000000, 0x40080000, 0x42000000,
      0x00080100, 0x02000100, 0x40000100, 0x00080000,
      0x00000000, 0x40080000, 0x02080100, 0x40000100};

  private static int SP6[] = {
      0x20000010, 0x20400000, 0x00004000, 0x20404010,
      0x20400000, 0x00000010, 0x20404010, 0x00400000,
      0x20004000, 0x00404010, 0x00400000, 0x20000010,
      0x00400010, 0x20004000, 0x20000000, 0x00004010,
      0x00000000, 0x00400010, 0x20004010, 0x00004000,
      0x00404000, 0x20004010, 0x00000010, 0x20400010,
      0x20400010, 0x00000000, 0x00404010, 0x20404000,
      0x00004010, 0x00404000, 0x20404000, 0x20000000,
      0x20004000, 0x00000010, 0x20400010, 0x00404000,
      0x20404010, 0x00400000, 0x00004010, 0x20000010,
      0x00400000, 0x20004000, 0x20000000, 0x00004010,
      0x20000010, 0x20404010, 0x00404000, 0x20400000,
      0x00404010, 0x20404000, 0x00000000, 0x20400010,
      0x00000010, 0x00004000, 0x20400000, 0x00404010,
      0x00004000, 0x00400010, 0x20004010, 0x00000000,
      0x20404000, 0x20000000, 0x00400010, 0x20004010};

  private static int SP7[] = {
      0x00200000, 0x04200002, 0x04000802, 0x00000000,
      0x00000800, 0x04000802, 0x00200802, 0x04200800,
      0x04200802, 0x00200000, 0x00000000, 0x04000002,
      0x00000002, 0x04000000, 0x04200002, 0x00000802,
      0x04000800, 0x00200802, 0x00200002, 0x04000800,
      0x04000002, 0x04200000, 0x04200800, 0x00200002,
      0x04200000, 0x00000800, 0x00000802, 0x04200802,
      0x00200800, 0x00000002, 0x04000000, 0x00200800,
      0x04000000, 0x00200800, 0x00200000, 0x04000802,
      0x04000802, 0x04200002, 0x04200002, 0x00000002,
      0x00200002, 0x04000000, 0x04000800, 0x00200000,
      0x04200800, 0x00000802, 0x00200802, 0x04200800,
      0x00000802, 0x04000002, 0x04200802, 0x04200000,
      0x00200800, 0x00000000, 0x00000002, 0x04200802,
      0x00000000, 0x00200802, 0x04200000, 0x00000800,
      0x04000002, 0x04000800, 0x00000800, 0x00200002};

  private static int SP8[] = {
      0x10001040, 0x00001000, 0x00040000, 0x10041040,
      0x10000000, 0x10001040, 0x00000040, 0x10000000,
      0x00040040, 0x10040000, 0x10041040, 0x00041000,
      0x10041000, 0x00041040, 0x00001000, 0x00000040,
      0x10040000, 0x10000040, 0x10001000, 0x00001040,
      0x00041000, 0x00040040, 0x10040040, 0x10041000,
      0x00001040, 0x00000000, 0x00000000, 0x10040040,
      0x10000040, 0x10001000, 0x00041040, 0x00040000,
      0x00041040, 0x00040000, 0x10041000, 0x00001000,
      0x00000040, 0x10040040, 0x00001000, 0x00041040,
      0x10001000, 0x00000040, 0x10000040, 0x10040000,
      0x10040040, 0x10000000, 0x00040000, 0x10001040,
      0x00000000, 0x10041040, 0x00040040, 0x10000040,
      0x10040000, 0x10001000, 0x10001040, 0x00000000,
      0x10041040, 0x00041000, 0x00041000, 0x00001040,
      0x00001040, 0x00040040, 0x10000000, 0x10041000};

  private int encrypt(char key[], char data[], int blocks) {
    if ( (data.length == 0) || (blocks < 1)) {
      return 0;
    }
    deskey(key, false);
    des(data, data, blocks);
    return 1;
  }

  int decrypt(char key[], char data[], int blocks) {
    if ( (data.length == 0) || (blocks < 1)) {
      return 0;
    }
    deskey(key, true);
    des(data, data, blocks);
    return 1;
  }

  /* int yencrypt ( char key[], char data[], int size )
   {
      if ((data.length==0)||(size<1))
         return 0;
      // The last char of data is bitwise complemented and filled the rest
      // buffer.If size is 16, it will extend to 24,and 17 still 24.
      char lastChar = data[size-1];
      int  blocks = size/8+1;
      memset (data+size, ~lastChar, blocks*8-size);
      deskey ( key, true );
      return encrypt ( data, data, blocks);
   }
   */
  int ydecrypt(char key[], char data[], int blocks, int size) {
    if ( (data.length == 0) || (blocks < 1)) {
      return 0;
    }

    deskey(key, true);
    if (decrypt(data, data, blocks) == 0) {
      return 0;
    }
    if (size != 0) {
      int pos = blocks * 8 - 1;
      char endChar = data[pos];
      while ( (pos > 0) && (data[pos] == endChar)) {
        pos--;
      }
      if (data[pos] != ~endChar) {
        return 0;
      }
      size = pos + 1;
    }
    return 1;
  }

// -----------------------------------------------------------------------
// des
//      Encrpts/Decrypts(according to the key currently loaded int the
//      internal key register) SOME blocks of eight bytes at address 'in'
//      into the block at address 'out'. They can be the same.
//
//      "in"
//      "out"
//      "block"  Number of blocks.
// -----------------------------------------------------------------------
  void des(char in[], char out[], int blocks) {
    char tempin[] = new char[8];
    char tempout[] = new char[8];
    int k = 0;
    int j;
    for (int i = 0; i < blocks; i++) {
      for (j = 0; j < 8; j++) {
        if ( (k * 8 + j) >= in.length) {
          break;
        }
        tempin[j] = in[k * 8 + j];
      }
      des_block(tempin, tempout);
      for (j = 0; j < 8; j++) {
        if ( (k * 8 + j) >= out.length) {
          break;
        }
        out[k * 8 + j] = tempout[j];
      }
      k++;
    }
  }

// -----------------------------------------------------------------------
// des_block
//      Encrpts/Decrypts(according to the key currently loaded int the
//      internal key register) one block of eight bytes at address 'in'
//      into the block at address 'out'. They can be the same.
//
//      "in"
//      "out"
// -----------------------------------------------------------------------
  void des_block(char in[], char out[]) {
    int work[] = new int[2];

    scrunch(in, work);
    desfunc(work, KnL);
    unscrun(work, out);
  }

// ----------------------------------------------------------------------
// deskey
//       Sets the internal key register (KnR) according to the hexadecimal
//       key contained in the 8 bytes of hexkey, according to the DES,
//       for encryption or decrytion according to MODE
//
//       "key" is the 64 bits key.
//       "md"  means encryption or decryption.
// ----------------------------------------------------------------------
  void deskey(char key[], boolean IsDescrypt) /* Thanks to James Gillogly &am
     p; Phil Karn! */
  {
    int i, j, l, m, n;
    char pc1m[] = new char[56];
    char pcr[] = new char[56];
    int kn[] = new int[32];

    for (j = 0; j < 56; j++) {
      l = pc1[j];
      m = l & 07;
      if ( (key[l >>> 3] & bytebit[m]) == 0) {
        pc1m[j] = 0;
      }
      else {
        pc1m[j] = 1;
      }
    }
    for (i = 0; i < 16; i++) {
      if (IsDescrypt == true) {
        m = (15 - i) << 1;
      }
      else {
        m = i << 1;
      }
      n = m + 1;
      kn[m] = kn[n] = 0;
      for (j = 0; j < 28; j++) {
        l = j + totrot[i];
        if (l < 28) {
          pcr[j] = pc1m[l];
        }
        else {
          pcr[j] = pc1m[l - 28];
        }
      }
      for (j = 28; j < 56; j++) {
        l = j + totrot[i];
        if (l < 56) {
          pcr[j] = pc1m[l];
        }
        else {
          pcr[j] = pc1m[l - 28];
        }
      }
      for (j = 0; j < 24; j++) {
        if (pcr[pc2[j]] != 0) {
          kn[m] |= bigbyte[j];
        }
        if (pcr[pc2[j + 24]] != 0) {
          kn[n] |= bigbyte[j];
        }
      }
    }
    cookey(kn);
    return;
  }

// ----------------------------------------------------------------------
// cookey
//       Only called by deskey.
// -----------------------------------------------------------------------
  void cookey(int raw1[]) {
    int raw0;
    int dough[] = new int[32];
    int i;
    int j = 0;
    int k = 0;
    for (i = 0; i < 16; i++) {
      raw0 = raw1[k++];
      dough[j] = (raw0 & 0x00fc0000) << 6;
      dough[j] |= (raw0 & 0x00000fc0) << 10;
      dough[j] |= (raw1[k] & 0x00fc0000) >>> 10;
      dough[j++] |= (raw1[k] & 0x00000fc0) >>> 6;
      dough[j] = (raw0 & 0x0003f000) << 12;
      dough[j] |= (raw0 & 0x0000003f) << 16;
      dough[j] |= (raw1[k] & 0x0003f000) >>> 4;
      dough[j++] |= (raw1[k] & 0x0000003f);
      k++;
    }
    usekey(dough);
    return;
  }

// ----------------------------------------------------------------------
// usekey
//       Only called by cookey.
//       Loads the interal key register with the data in cookedkey.
// -----------------------------------------------------------------------
  void usekey(int from[]) {
    int i;
    for (i = 0; i < 32; i++) {
      KnL[i] = from[i];
    }
    return;
  }

  void scrunch(char outof[], int into[]) {
    into[0] = (outof[0] & 0xff) << 24;
    into[0] |= (outof[1] & 0xff) << 16;
    into[0] |= (outof[2] & 0xff) << 8;
    into[0] |= (outof[3] & 0xff);
    into[1] = (outof[4] & 0xff) << 24;
    into[1] |= (outof[5] & 0xff) << 16;
    into[1] |= (outof[6] & 0xff) << 8;
    into[1] |= (outof[7] & 0xff);
    return;
  }

  void unscrun(int outof[], char into[]) {
    into[0] = (char) ( (outof[0] >>> 24) & 0xffL);
    into[1] = (char) ( (outof[0] >>> 16) & 0xffL);
    into[2] = (char) ( (outof[0] >>> 8) & 0xffL);
    into[3] = (char) (outof[0] & 0xffL);
    into[4] = (char) ( (outof[1] >>> 24) & 0xffL);
    into[5] = (char) ( (outof[1] >>> 16) & 0xffL);
    into[6] = (char) ( (outof[1] >>> 8) & 0xffL);
    into[7] = (char) (outof[1] & 0xffL);
    return;
  }

  void desfunc(int block[], int keys[]) {
    int fval, work, right, leftt;
    int round;

    leftt = block[0];
    right = block[1];
    work = ( (leftt >>> 4) ^ right) & 0x0f0f0f0f;
    right ^= work;
    leftt ^= (work << 4);
    work = ( (leftt >>> 16) ^ right) & 0x0000ffff;
    right ^= work;
    leftt ^= (work << 16);
    work = ( (right >>> 2) ^ leftt) & 0x33333333;
    leftt ^= work;
    right ^= (work << 2);
    work = ( (right >>> 8) ^ leftt) & 0x00ff00ff;
    leftt ^= work;
    right ^= (work << 8);
    right = ( (right << 1) | ( (right >>> 31) & 1)) & 0xffffffff;
    work = (leftt ^ right) & 0xaaaaaaaa;
    leftt ^= work;
    right ^= work;
    leftt = ( (leftt << 1) | ( (leftt >>> 31) & 1)) & 0xffffffff;
    int i = 0;
    for (round = 0; round < 8; round++) {
      work = (right << 28) | (right >>> 4);
      work ^= keys[i];
      i++;
      fval = SP7[ (int) (work & 0x3fL)];
      fval |= SP5[ (int) ( (work >>> 8) & 0x3fL)];
      fval |= SP3[ (int) ( (work >>> 16) & 0x3fL)];
      fval |= SP1[ (int) ( (work >>> 24) & 0x3fL)];
      work = right ^ keys[i];
      i++;
      fval |= SP8[ (int) (work & 0x3fL)];
      fval |= SP6[ (int) ( (work >>> 8) & 0x3fL)];
      fval |= SP4[ (int) ( (work >>> 16) & 0x3fL)];
      fval |= SP2[ (int) ( (work >>> 24) & 0x3fL)];
      leftt ^= fval;
      work = (leftt << 28) | (leftt >>> 4);
      work ^= keys[i];
      i++;
      fval = SP7[ (int) (work & 0x3fL)];
      fval |= SP5[ (int) ( (work >>> 8) & 0x3fL)];
      fval |= SP3[ (int) ( (work >>> 16) & 0x3fL)];
      fval |= SP1[ (int) ( (work >>> 24) & 0x3fL)];
      work = leftt ^ keys[i];
      i++;
      fval |= SP8[ (int) (work & 0x3fL)];
      fval |= SP6[ (int) ( (work >>> 8) & 0x3fL)];
      fval |= SP4[ (int) ( (work >>> 16) & 0x3fL)];
      fval |= SP2[ (int) ( (work >>> 24) & 0x3fL)];
      right ^= fval;
    }
    right = (right << 31) | (right >>> 1);
    work = (leftt ^ right) & 0xaaaaaaaa;
    leftt ^= work;
    right ^= work;
    leftt = (leftt << 31) | (leftt >>> 1);
    work = ( (leftt >>> 8) ^ right) & 0x00ff00ff;
    right ^= work;
    leftt ^= (work << 8);
    work = ( (leftt >>> 2) ^ right) & 0x33333333;
    right ^= work;
    leftt ^= (work << 2);
    work = ( (right >>> 16) ^ leftt) & 0x0000ffff;
    leftt ^= work;
    right ^= (work << 16);
    work = ( (right >>> 4) ^ leftt) & 0x0f0f0f0f;
    leftt ^= work;
    right ^= (work << 4);
    block[0] = right;
    block[1] = leftt;
    return;
  }

// -----------------------------------------------------------------------
// Initial of static data members. These data will be used by all the
// instances of class,and can not be changed.
// -----------------------------------------------------------------------
  public Descrypt() {

  }

  public StringBuffer StrEnscrypt(StringBuffer input,StringBuffer output) {
    char data[];
    char key[] = new char[8];
    char tempchar[] = new char[8];
    String gg = new String();
    StringBuffer gg1 = new StringBuffer();
    key = "banknewd".toCharArray();
    int k = 0;
    int j = 0;
    if (output.length() > 0) {
      output.delete(0, output.length());

    }
    int i = input.length() % 8;

    if (i != 0) {
      i = 8 - i;

    }
    data = new char[input.length() + i];
    input.getChars(0, input.length(), data, 0);
    for (j = 0; j < i; j++) {
      data[j + input.length()] = ' ';

    }
    i = data.length / 8;
    long templ = 0;
    ByteBuffer tempb = ByteBuffer.allocate(8);
    for (j = 0; j < i; j++) {
      tempchar[0] = data[j * 8 + 0];
      tempchar[1] = data[j * 8 + 1];
      tempchar[2] = data[j * 8 + 2];
      tempchar[3] = data[j * 8 + 3];
      tempchar[4] = data[j * 8 + 4];
      tempchar[5] = data[j * 8 + 5];
      tempchar[6] = data[j * 8 + 6];
      tempchar[7] = data[j * 8 + 7];
      encrypt(key, tempchar, 1);
      gg1.delete(0, gg1.length());

      gg = Integer.toBinaryString( (int) tempchar[0]);
      gg1.insert(0, gg);
      if (gg.length() < 8) {
        for (k = 0; k < (8 - gg.length()); k++) {
          gg1.insert(0, '0');
        }
      }

      gg = Integer.toBinaryString( (int) tempchar[1]);
      gg1.insert(0, gg);
      if (gg.length() < 8) {
        for (k = 0; k < (8 - gg.length()); k++) {
          gg1.insert(0, '0');
        }
      }

      gg = Integer.toBinaryString( (int) tempchar[2]);
      gg1.insert(0, gg);
      if (gg.length() < 8) {
        for (k = 0; k < (8 - gg.length()); k++) {
          gg1.insert(0, '0');
        }
      }

      gg = Integer.toBinaryString( (int) tempchar[3]);
      gg1.insert(0, gg);
      if (gg.length() < 8) {
        for (k = 0; k < (8 - gg.length()); k++) {
          gg1.insert(0, '0');
        }
      }
      gg = Integer.toBinaryString( (int) tempchar[4]);
      gg1.insert(0, gg);
      if (gg.length() < 8) {
        for (k = 0; k < (8 - gg.length()); k++) {
          gg1.insert(0, '0');
        }
      }

      gg = Integer.toBinaryString( (int) tempchar[5]);
      gg1.insert(0, gg);
      if (gg.length() < 8) {
        for (k = 0; k < (8 - gg.length()); k++) {
          gg1.insert(0, '0');
        }
      }

      gg = Integer.toBinaryString( (int) tempchar[6]);
      gg1.insert(0, gg);
      if (gg.length() < 8) {
        for (k = 0; k < (8 - gg.length()); k++) {
          gg1.insert(0, '0');
        }
      }

      gg = Integer.toBinaryString( (int) tempchar[7]);
      gg1.insert(0, gg);
      if (gg.length() < 8) {
        for (k = 0; k < (8 - gg.length()); k++) {
          gg1.insert(0, '0');
        }
      }

      if (gg1.charAt(0) == '0') {
        gg1.deleteCharAt(0);
        templ = Long.parseLong(gg1.toString(), 2);
      }
      else {
        gg1.deleteCharAt(0);
        gg1.insert(0, '-');
        for (k = 0; k < gg1.length(); k++) {
          if (gg1.charAt(k) == '0') {
            gg1.setCharAt(k, '1');
          }
          else if (gg1.charAt(k) == '1') {
            gg1.setCharAt(k, '0');
          }
        }
        templ = Long.parseLong(gg1.toString(), 2);
        templ -= 1;
      }

      output.append(templ);
      output.append('/');
    }
    return output;
  }

  public StringBuffer StrDescrypt(StringBuffer input, StringBuffer output) {
    char data[];
    char key[] = new char[8];
    key = "banknewd".toCharArray();
    data = new char[8];

    if (output.length() > 0) {
      output.delete(0, output.length());
    }
    int prepos, pos;
    pos = 0;
    while (pos < input.length()) {
      if (input.charAt(pos) == '/') {
        break;
      }
      pos++;
    }
    if (pos == 0) {
      return null;
    }
    int k = 0;
    Byte tempb[] = new Byte[8];
    prepos = 0;
    StringBuffer ss = new StringBuffer();
    long dd;
    while (pos > 0 && pos < input.length()) {
      ss.delete(0, ss.length());
      ss.append(input.substring(prepos, pos));
      dd = Long.parseLong(ss.toString());
      ss.delete(0, ss.length());

      ss.append(Long.toBinaryString(dd));

      while (ss.length() != 64) {
        ss.insert(0, '0');

      }
      for (k = 0; k < 8; k++) {
        data[7 -
            k] = (char) Integer.parseInt(ss.substring(k * 8, 8 * (k + 1)), 2);
      }

      decrypt(key, data, 1);
      output.append(data);
      pos++;
      prepos = pos;
      while (pos < input.length()) {
        if (input.charAt(pos) == '/') {
          break;
        }
        pos++;
      }
    }
    int i = output.length() - 1;
    while (i > 0) {
      if (output.charAt(i) != ' ') {
        break;
      }
      else {
        output.deleteCharAt(i);
      }
      i--;
    }
    System.out.println("output="+output.toString());
    return output;
  }

  public static void main(String[] args) {
    Descrypt descrypt1 = new Descrypt();
    char key[] = {
        '1', '2', '3', '4', '5', '6', '7', '8'};
    char data[] = {
        '1', '2', '3', '4', '5', '6', '7', '0', '0', '0'};
    descrypt1.encrypt(key, data, 1);
    descrypt1.decrypt(key, data, 1);

    StringBuffer data1 = new StringBuffer("213123151234");
    char data2[] = new char[8];

    StringBuffer out = new StringBuffer("");
    descrypt1.StrEnscrypt(data1, out);
    descrypt1.StrDescrypt(out, data1);
  }
}
