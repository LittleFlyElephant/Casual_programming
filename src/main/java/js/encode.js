// Transcrypt'ed from Python, 2018-10-14 16:37:42
import {AssertionError, AttributeError, BaseException, DeprecationWarning, Exception, IndexError, IterableError, KeyError, NotImplementedError, RuntimeWarning, StopIteration, UserWarning, ValueError, Warning, __JsIterator__, __PyIterator__, __Terminal__, __add__, __and__, __call__, __class__, __envir__, __eq__, __floordiv__, __ge__, __get__, __getcm__, __getitem__, __getslice__, __getsm__, __gt__, __i__, __iadd__, __iand__, __idiv__, __ijsmod__, __ilshift__, __imatmul__, __imod__, __imul__, __in__, __init__, __ior__, __ipow__, __irshift__, __isub__, __ixor__, __jsUsePyNext__, __jsmod__, __k__, __kwargtrans__, __le__, __lshift__, __lt__, __matmul__, __mergefields__, __mergekwargtrans__, __mod__, __mul__, __ne__, __neg__, __nest__, __or__, __pow__, __pragma__, __proxy__, __pyUseJsNext__, __rshift__, __setitem__, __setproperty__, __setslice__, __sort__, __specialattrib__, __sub__, __super__, __t__, __terminal__, __truediv__, __withblock__, __xor__, abs, all, any, assert, bool, bytearray, bytes, callable, chr, copy, deepcopy, delattr, dict, dir, divmod, enumerate, filter, float, getattr, hasattr, input, int, isinstance, issubclass, len, list, map, max, min, object, ord, pow, print, property, py_TypeError, py_iter, py_metatype, py_next, py_reversed, py_typeof, range, repr, round, set, setattr, sorted, str, sum, tuple, zip} from './org.transcrypt.__runtime__.js';
var __name__ = '__main__';
export var shift_up = list ([2, 7, 11, 15, 19, 23]);
export var shift_down = list ([0, 4, 9, 13, 17, 21]);
export var blank = 2;
export var n1 = int ('11101100', 2);
export var n2 = int ('00010001', 2);
export var get_next = function (arr, x, y, size) {
    if (__in__ (y, shift_up)) {
        var tmp = x - 1;
        while (tmp >= 0 && arr [tmp] [y + 1] != blank) {
            var tmp = tmp - 1;
        }
        if (tmp < 0) {
            var y = y - 1;
            if (arr [x] [y] != blank) {
                var y = y - 1;
            }
            return tuple ([x, y]);
        }
        else {
            return tuple ([tmp, y + 1]);
        }
    }
    if (__in__ (y, shift_down)) {
        var tmp = x + 1;
        while (tmp < size && arr [tmp] [y + 1] != blank) {
            var tmp = tmp + 1;
        }
        if (tmp == size) {
            var y = y - 1;
            if (y == 8) {
                return tuple ([size - 9, y]);
            }
            if (arr [x] [y] != blank) {
                var y = y - 1;
            }
            return tuple ([x, y]);
        }
        else {
            return tuple ([tmp, y + 1]);
        }
    }
    return tuple ([x, y - 1]);
};
export var get_qr_graph = function (payload, size) {
    var arr = (function () {
        var __accu0__ = [];
        for (var i = 0; i < size; i++) {
            __accu0__.append (list ([blank]) * size);
        }
        return __accu0__;
    }) ();
    var position = list ([list ([1, 1, 1, 1, 1, 1, 1, 0]), list ([1, 0, 0, 0, 0, 0, 1, 0]), list ([1, 0, 1, 1, 1, 0, 1, 0]), list ([1, 0, 1, 1, 1, 0, 1, 0]), list ([1, 0, 1, 1, 1, 0, 1, 0]), list ([1, 0, 0, 0, 0, 0, 1, 0]), list ([1, 1, 1, 1, 1, 1, 1, 0]), list ([0, 0, 0, 0, 0, 0, 0, 0])]);
    for (var i = 0; i < 8; i++) {
        for (var j = 0; j < 8; j++) {
            arr [i] [j] = position [i] [j];
            arr [i] [(size - j) - 1] = position [i] [j];
            arr [(size - i) - 1] [j] = position [i] [j];
        }
    }
    var align = list ([list ([1, 1, 1, 1, 1]), list ([1, 0, 0, 0, 1]), list ([1, 0, 1, 0, 1]), list ([1, 0, 0, 0, 1]), list ([1, 1, 1, 1, 1])]);
    for (var i = 0; i < 8; i++) {
        arr [i] [8] = 0;
        arr [(size - 1) - i] [8] = 0;
    }
    if (size == 25) {
        for (var i = 0; i < 5; i++) {
            for (var j = 0; j < 5; j++) {
                arr [16 + i] [16 + j] = align [i] [j];
            }
        }
    }
    for (var i = 0; i < size - 8 * 2; i++) {
        arr [6] [8 + i] = __mod__ (i + 1, 2);
        arr [8 + i] [6] = __mod__ (i + 1, 2);
    }
    var px = size - 1;
    var py = size - 1;
    for (var data of payload) {
        for (var i = 0; i < 8; i++) {
            arr [px] [py] = data >> 7 - i & 1;
            var __left0__ = get_next (arr, px, py, size);
            var px = __left0__ [0];
            var py = __left0__ [1];
        }
    }
    while (1) {
        if (py < 0) {
            break;
        }
        for (var i = 0; i < 8; i++) {
            if (py < 0) {
                break;
            }
            arr [px] [py] = n1 >> 7 - i & 1;
            var __left0__ = get_next (arr, px, py, size);
            var px = __left0__ [0];
            var py = __left0__ [1];
        }
        for (var i = 0; i < 8; i++) {
            if (py < 0) {
                break;
            }
            arr [px] [py] = n2 >> 7 - i & 1;
            var __left0__ = get_next (arr, px, py, size);
            var px = __left0__ [0];
            var py = __left0__ [1];
        }
    }
    for (var i = 0; i < size; i++) {
        for (var j = 0; j < size; j++) {
            if (arr [i] [j] == blank) {
                arr [i] [j] = 0;
            }
        }
    }
    return arr;
};
export var generateintarray = function (message) {
    var cleanmsg = ''.join ((function () {
        var __accu0__ = [];
        for (var i of message) {
            __accu0__.append ((ord (i) < 128 ? i : ''));
        }
        return __accu0__;
    }) ());
    var result = list ([]);
    result.append (len (cleanmsg));
    for (var i of cleanmsg) {
        result.append (ord (i));
        result.append ((__mod__ (bin (ord (i)).count ('1'), 2) == 0 ? 0 : 1));
    }
    if (result [0] <= 14) {
        return tuple ([21, result]);
    }
    return tuple ([25, result]);
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
export var doXor = function (map, QRcode) {
    var rbits = getmapReverseBits (map);
    var bits = list ([]);
    var clen = len (QRcode);
    for (var i = 0; i < clen * clen; i++) {
        var row = Math.floor (i / clen);
        var col = __mod__ (i, clen);
        bits.append (rbits [i] ^ QRcode [row] [col]);
    }
    return bits;
};
export var fourBits2hex = function (bits, low, high) {
    var switchcases = dict ({0: '0', 1: '1', 2: '2', 3: '3', 4: '4', 5: '5', 6: '6', 7: '7', 8: '8', 9: '9', 10: 'a', 11: 'b', 12: 'c', 13: 'd', 14: 'e', 15: 'f'});
    var hexvalue = 0;
    var base = 1;
    for (var j = low; j > high; j--) {
        hexvalue += base * bits [j];
        base *= 2;
    }
    return switchcases.py_get (hexvalue);
};
export var bits2hex = function (bits) {
    var hexstr = '';
    var prefix = '0x';
    var toappendlist = (function () {
        var __accu0__ = [];
        for (var i = 0; i < 32 - __mod__ (len (bits), 32); i++) {
            __accu0__.append (0);
        }
        return __accu0__;
    }) ();
    var appended = bits.__getslice__ (0, 32 * (Math.floor (len (bits) / 32)), 1) + toappendlist;
    var appended = appended + bits.__getslice__ (32 * (Math.floor (len (bits) / 32)), null, 1);
    for (var i = 0; i < len (appended); i += 32) {
        hexstr += prefix;
        var startzero = 1;
        for (var j = 0; j < 8; j++) {
            var hex = fourBits2hex (appended, (i + 3) + 4 * j, (i - 1) + 4 * j);
            if (hex != '0') {
                var startzero = 0;
                hexstr += hex;
            }
            if (hex == '0' && startzero == 0) {
                hexstr += hex;
            }
        }
    }
    return hexstr;
};
export var encryptQR = function (sidelen, QRcode) {
    var maplen = Math.floor ((sidelen * sidelen) / 8) + 1;
    var map = (function () {
        var __accu0__ = [];
        for (var i = 0; i < maplen; i++) {
            __accu0__.append (0.0);
        }
        return __accu0__;
    }) ();
    formMap (map);
    var bits = doXor (map, QRcode);
    var result = bits2hex (bits);
    return result;
};
export var encode = function (message) {
    var __left0__ = generateintarray (message);
    var size = __left0__ [0];
    var arr = __left0__ [1];
    var arr2 = get_qr_graph (arr, size);
    var encoded = encryptQR (size, arr2);
    return encoded;
};
export var print_hex = function (hex, size) {
    var arr = hex.py_split ('0x');
    var count = 0;
    for (var j = 0; j < len (arr) - 1; j++) {
        if (j > 0) {
            var bn = int (arr [j], 16);
            var list = list ([]);
            for (var i = 0; i < 32; i++) {
                list.insert (0, bn >> i & 1);
            }
            for (var i of list) {
                print (i, __kwargtrans__ ({end: ' '}));
                var count = count + 1;
                if (__mod__ (count, size) == 0) {
                    print ();
                }
            }
        }
    }
    var last = arr [len (arr) - 1];
    var num = __mod__ (size * size, 32) - 4 * len (last);
    for (var i = 0; i < num; i++) {
        print (0, __kwargtrans__ ({end: ' '}));
        var count = count + 1;
        if (__mod__ (count, size) == 0) {
            print ();
        }
    }
    var l = list ([]);
    for (var i = 0; i < len (last); i++) {
        for (var j = 0; j < 4; j++) {
            var k = int (last [(len (last) - 1) - i], 16);
            l.insert (0, k >> j & 1);
        }
    }
    for (var i of l) {
        print (i, __kwargtrans__ ({end: ' '}));
        var count = count + 1;
        if (__mod__ (count, size) == 0) {
            print ();
        }
    }
};

//# sourceMappingURL=encode.map