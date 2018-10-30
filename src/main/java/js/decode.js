// Transcrypt'ed from Python, 2018-10-14 16:15:18
import {__in__, __mod__, chr, enumerate, float, int, len, list, print, range, str, tuple} from './org.transcrypt.__runtime__.js';
var __name__ = '__main__';
export var shift_up = [2, 7, 11, 15, 19, 23];
export var shift_down = [0, 4, 9, 13, 17, 21];
export var blank = 2;
export var n1 = parseInt ('11101100', 2);
export var n2 = parseInt ('00010001', 2);

export var arraytomsg = function (array) {
    if (len (array) == 0) {
        return ;
    }
    var length = array [0];
    var msg = '';
    var i = 1;
    while (i < len (array)) {
        msg += chr (array [i]);
        var errorcode = (__mod__ (bin (array [i]).count ('1'), 2) == 0 ? 0 : 1);
        if (errorcode != array [i + 1]) {
            print ('The massage is corrupted!!');
        }
        i += 2;
    }
    return msg;
};

export var get_next = function (arr, x, y, size) {
    if (shift_up.includes(y)) {
        var tmp = x - 1;
        while (tmp >= 0 && arr[tmp][y + 1] === blank) {
            tmp = tmp - 1;
        }
        if (tmp < 0) {
            y = y - 1;
            if (arr [x] [y] === blank) {
                y = y - 1;
            }
            return [x, y];
        }
        else {
            return [tmp, y + 1];
        }
    }
    if (shift_down.includes(y)) {
        var tmp = x + 1;
        while (tmp < size && arr [tmp] [y + 1] === blank) {
            tmp = tmp + 1;
        }
        if (tmp === size) {
            y = y - 1;
            if (y === 8) {
                return [size - 9, y];
            }
            if (arr[x][y] === blank) {
                y = y - 1;
            }
            return [x, y];
        }
        else {
            return [tmp, y + 1];
        }
    }
    return [x, y - 1];
};

export var get_payload = function (arr, size) {
    var position = [[1, 1, 1, 1, 1, 1, 1, 0],
        [1, 0, 0, 0, 0, 0, 1, 0],
        [1, 0, 1, 1, 1, 0, 1, 0],
        [1, 0, 1, 1, 1, 0, 1, 0],
        [1, 0, 1, 1, 1, 0, 1, 0],
        [1, 0, 0, 0, 0, 0, 1, 0],
        [1, 1, 1, 1, 1, 1, 1, 0],
        [0, 0, 0, 0, 0, 0, 0, 0]];
    for (var i = 0; i < 8; i++) {
        for (var j = 0; j < 8; j++) {
            if (arr [i] [j] != position [i] [j]) {
                return [];
            }
            if (arr [i] [(size - j) - 1] != position [i] [j]) {
                return [];
            }
            if (arr [(size - i) - 1] [j] != position [i] [j]) {
                return [];
            }
        }
    }
    var align = list ([list ([1, 1, 1, 1, 1]), list ([1, 0, 0, 0, 1]), list ([1, 0, 1, 0, 1]), list ([1, 0, 0, 0, 1]), list ([1, 1, 1, 1, 1])]);
    if (size == 25) {
        for (var i = 0; i < 5; i++) {
            for (var j = 0; j < 5; j++) {
                if (arr [16 + i] [16 + j] != align [i] [j]) {
                    return list ([]);
                }
            }
        }
    }
    for (var i = 0; i < size - 8 * 2; i++) {
        if (arr [6] [8 + i] != __mod__ (i + 1, 2)) {
            return list ([]);
        }
        if (arr [8 + i] [6] != __mod__ (i + 1, 2)) {
            return list ([]);
        }
    }
    for (var i = 0; i < 8; i++) {
        for (var j = 0; j < 8; j++) {
            arr [i] [j] = blank;
            arr [i] [(size - j) - 1] = blank;
            arr [(size - i) - 1] [j] = blank;
        }
    }
    for (var i = 0; i < 8; i++) {
        arr [i] [8] = blank;
        arr [(size - 1) - i] [8] = blank;
    }
    if (size == 25) {
        for (var i = 0; i < 5; i++) {
            for (var j = 0; j < 5; j++) {
                arr [16 + i] [16 + j] = blank;
            }
        }
    }
    for (var i = 0; i < size - 8 * 2; i++) {
        arr [6] [8 + i] = blank;
        arr [8 + i] [6] = blank;
    }
    var px = size - 1;
    var py = size - 1;
    var len = 0;
    for (var i = 0; i < 8; i++) {
        var num = arr [px] [py] & 1;
        var len = len + (num << 7 - i);
        var __left0__ = get_next (arr, px, py, size);
        var px = __left0__ [0];
        var py = __left0__ [1];
    }
    var ret = list ([]);
    ret.append (len);
    for (var i = 0; i < len * 2; i++) {
        var d = 0;
        for (var j = 0; j < 8; j++) {
            var num = arr [px] [py] & 1;
            var d = d + (num << 7 - j);
            var __left0__ = get_next (arr, px, py, size);
            var px = __left0__ [0];
            var py = __left0__ [1];
        }
        ret.append (d);
    }
    return ret;
};
export var get_payload_outer = function (arr, size) {
    var res = get_payload (arr, size);
    if (len (res) != 0) {
        return arraytomsg (res);
    }
    var b = (function () {
        var __accu0__ = [];
        for (var i = 0; i < size; i++) {
            __accu0__.append (list ([blank]) * size);
        }
        return __accu0__;
    }) ();
    for (var i = 0; i < size; i++) {
        for (var j = 0; j < size; j++) {
            b [j] [(size - 1) - i] = arr [i] [j];
        }
    }
    var res = get_payload (b, size);
    if (len (res) != 0) {
        return arraytomsg (res);
    }
    for (var i = 0; i < size; i++) {
        for (var j = 0; j < size; j++) {
            b [(size - 1) - i] [(size - 1) - j] = arr [i] [j];
        }
    }
    var res = get_payload (b, size);
    if (len (res) != 0) {
        return arraytomsg (res);
    }
    for (var i = 0; i < size; i++) {
        for (var j = 0; j < size; j++) {
            b [(size - 1) - j] [i] = arr [i] [j];
        }
    }
    var res = get_payload (b, size);
    if (len (res) != 0) {
        return arraytomsg (res);
    }
    return '';
};
export var formBaseMap = function (map) {
    var mlen = len (map);
    map [0] = 0.1;
    var r = 4.0;
    for (var i = 1; i < mlen; i++) {
        map [i] = (r * map [i - 1]) * (1 - map [i - 1]);
    }
};
export var formMap = function (map) {
    formBaseMap (map);
    var mlen = len (map);
    for (var i = 0; i < mlen; i++) {
        map [i] = float (int (map [i] * 255));
    }
};
export var getmapReverseBits = function (map) {
    var bits = list ([]);
    for (var [i, x] of enumerate (map)) {
        var x = int (x);
        var count = 0;
        while (x > 0) {
            bits.append (__mod__ (x, 2));
            var x = Math.floor (x / 2);
            count++;
        }
        while (count < 8) {
            bits.append (0);
            count++;
        }
    }
    return bits;
};
export var doStrXor = function (map, QRcode) {
    var rbits = getmapReverseBits (map);
    var bits = list ([]);
    var clen = len (QRcode);
    for (var i = 0; i < clen * clen; i++) {
        var row = Math.floor (i / clen);
        var col = __mod__ (i, clen);
        bits.append (rbits [i] ^ int (QRcode [row] [col]));
    }
    return bits;
};
export var decryptQR = function (input) {
    var input = input.__getslice__ (2, null, 1);
    var my_hexdatas = input.py_split ('0x');
    var versionlen = 32;
    var allbits = '';
    for (var i = 0; i < len (my_hexdatas) - 1; i++) {
        var my_hexdata = my_hexdatas [i];
        var scale = 16;
        var num_of_bits = 8;
        var strp = str (bin (int (my_hexdata, scale)).__getslice__ (2, null, 1).zfill (num_of_bits));
        var toappendlist = '0' * (32 - len (strp));
        var strp = toappendlist + strp;
        allbits += strp;
    }
    var lastblocklen = versionlen * versionlen - 32 * (len (my_hexdatas) - 1);
    var lastblockhex = my_hexdatas [len (my_hexdatas) - 1];
    var strp = str (bin (int (lastblockhex, scale)).__getslice__ (2, null, 1).zfill (num_of_bits));
    var lasttoappendlist = '0' * (lastblocklen - len (strp));
    var strp = lasttoappendlist + strp;
    allbits += strp;
    var __left0__ = tuple ([len (allbits), versionlen]);
    var chunks = __left0__ [0];
    var chunk_size = __left0__ [1];
    var QRstrs = (function () {
        var __accu0__ = [];
        for (var i of range (0, chunks, chunk_size)) {
            __accu0__.append (allbits.__getslice__ (i, i + chunk_size, 1));
        }
        return __accu0__;
    }) ();
    var QRcode = list ([]);
    for (var j = 0; j < len (QRstrs); j++) {
        var __left0__ = tuple ([len (QRstrs [j]), 1]);
        var chunks = __left0__ [0];
        var chunk_size = __left0__ [1];
        var QRarray = (function () {
            var __accu0__ = [];
            for (var i of range (0, chunks, chunk_size)) {
                __accu0__.append (QRstrs [j].__getslice__ (i, i + chunk_size, 1));
            }
            return __accu0__;
        }) ();
        QRcode.append (QRarray);
    }
    var maplen = Math.floor ((versionlen * versionlen) / 8) + 1;
    var map = (function () {
        var __accu0__ = [];
        for (var i = 0; i < maplen; i++) {
            __accu0__.append (0.0);
        }
        return __accu0__;
    }) ();
    formMap (map);
    var bits = doStrXor (map, QRcode);
    var __left0__ = tuple ([len (bits), versionlen]);
    var chunks = __left0__ [0];
    var chunk_size = __left0__ [1];
    var QRcode = (function () {
        var __accu0__ = [];
        for (var i of range (0, chunks, chunk_size)) {
            __accu0__.append (bits.__getslice__ (i, i + chunk_size, 1));
        }
        return __accu0__;
    }) ();
    return QRcode;
};
export var loopMatrix = function (arr) {
    var list = (function () {
        var __accu0__ = [];
        for (var i = 0; i < 21; i++) {
            __accu0__.append (list ([blank]) * 21);
        }
        return __accu0__;
    }) ();
    for (var i = 0; i < (32 - 21) + 1; i++) {
        for (var j = 0; j < (32 - 21) + 1; j++) {
            for (var k = 0; k < 21; k++) {
                for (var l = 0; l < 21; l++) {
                    list [k] [l] = arr [i + k] [j + l];
                }
            }
            var str = get_payload_outer (list, 21);
            if (len (str) > 0) {
                return str;
            }
        }
    }
    var list = (function () {
        var __accu0__ = [];
        for (var i = 0; i < 25; i++) {
            __accu0__.append (list ([blank]) * 25);
        }
        return __accu0__;
    }) ();
    for (var i = 0; i < (32 - 25) + 1; i++) {
        for (var j = 0; j < (32 - 25) + 1; j++) {
            for (var k = 0; k < 25; k++) {
                for (var l = 0; l < 25; l++) {
                    list [k] [l] = arr [i + k] [j + l];
                }
            }
            var str = get_payload_outer (list, 25);
            if (len (str) > 0) {
                return str;
            }
        }
    }
    return '';
};

export var decode = function (msg) {
    var res1 = decryptQR (msg);
    return loopMatrix (res1);
};
