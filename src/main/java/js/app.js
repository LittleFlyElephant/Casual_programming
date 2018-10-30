const http = require('http');
const url = require('url');

const hostname = '0.0.0.0';
const port = 80;

// decode---

var shift_up = [2, 7, 11, 15, 19, 23];
var shift_down = [0, 4, 9, 13, 17, 21];
var blank = 2;

function arraytomsg (array) {
    if (array.length === 0) {
        return ;
    }
    var length = array[0];
    var msg = '';
    var i = 1;
    var ch;
    while (i < array.length) {
        ch = String.fromCharCode(array[i]);
        msg += ch;
        var errorcode = array[i].toString(2).match(/1/g).length % 2;
        if (errorcode !== array [i + 1]) {
            print ('The massage is corrupted!!');
        }
        i += 2;
    }
    return msg;
}

function get_next (arr, x, y, size) {
    var tmp;
    if (shift_up.includes(y)) {
        tmp = x - 1;
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
        tmp = x + 1;
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
}

function get_payload (arr, size) {
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
            if (arr [i] [j] !== position [i] [j]) {
                return [];
            }
            if (arr [i] [(size - j) - 1] !== position [i] [j]) {
                return [];
            }
            if (arr [(size - i) - 1] [j] !== position [i] [j]) {
                return [];
            }
        }
    }
    var align = [[1, 1, 1, 1, 1],
        [1, 0, 0, 0, 1],
        [1, 0, 1, 0, 1],
        [1, 0, 0, 0, 1],
        [1, 1, 1, 1, 1]];

    if (size === 25) {
        for (var i = 0; i < 5; i++) {
            for (var j = 0; j < 5; j++) {
                if (arr [16 + i] [16 + j] !== align [i] [j]) {
                    return [];
                }
            }
        }
    }
    for (var i = 0; i < size - 8 * 2; i++) {
        if (arr [6] [8 + i] !== (i + 1) % 2) {
            return [];
        }
        if (arr [8 + i] [6] !== (i + 1) % 2) {
            return [];
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
    if (size === 25) {
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
    var num;
    for (var i = 0; i < 8; i++) {
        num = arr [px] [py] & 1;
        len = len + (num << 7 - i);
        var next = get_next (arr, px, py, size);
        px = next [0];
        py = next [1];
    }
    var ret = [];
    ret.push(len);
    for (var i = 0; i < len * 2; i++) {
        var d = 0;
        for (var j = 0; j < 8; j++) {
            num = arr [px] [py] & 1;
            d = d + (num << 7 - j);
            var next = get_next (arr, px, py, size);
            px = next [0];
            py = next [1];
        }
        ret.push(d);
    }
    return ret;
}

function get_payload_outer(arr, size) {
    var res = get_payload (arr, size);
    if (res.length !== 0) {
        return arraytomsg (res);
    }

    var b = new Array(size);
    for (var i = 0; i < b.length; i++) {
        b[i] = new Array(size);
    }
    for (var i = 0; i < size; i++) {
        for (var j = 0; j < size; j++) {
            b [j] [(size - 1) - i] = arr [i] [j];
        }
    }
    res = get_payload (b, size);
    if (res.length !== 0) {
        return arraytomsg (res);
    }
    for (var i = 0; i < size; i++) {
        for (var j = 0; j < size; j++) {
            b [(size - 1) - i] [(size - 1) - j] = arr [i] [j];
        }
    }
    res = get_payload (b, size);
    if (res.length !== 0) {
        return arraytomsg (res);
    }
    for (var i = 0; i < size; i++) {
        for (var j = 0; j < size; j++) {
            b [(size - 1) - j] [i] = arr [i] [j];
        }
    }
    res = get_payload (b, size);
    if (res.length !== 0) {
        return arraytomsg (res);
    }
    return "";
}

function loopMatrix (arr) {
    var list = new Array(21);
    for (var i = 0; i < list.length; i++) {
        list[i] = new Array(21);
    }
    var str;
    for (var i = 0; i < (32 - 21) + 1; i++) {
        for (var j = 0; j < (32 - 21) + 1; j++) {
            for (var k = 0; k < 21; k++) {
                for (var l = 0; l < 21; l++) {
                    list [k] [l] = arr [i + k] [j + l];
                }
            }
            str = get_payload_outer (list, 21);
            if (str.length > 0) {
                return str;
            }
        }
    }
    list = new Array(25);
    for (var i = 0; i < list.length; i++) {
        list[i] = new Array(25);
    }
    for (var i = 0; i < (32 - 25) + 1; i++) {
        for (var j = 0; j < (32 - 25) + 1; j++) {
            for (var k = 0; k < 25; k++) {
                for (var l = 0; l < 25; l++) {
                    list [k] [l] = arr [i + k] [j + l];
                }
            }
            str = get_payload_outer (list, 25);
            if (str.length > 0) {
                return str;
            }
        }
    }
    return "";
}

function decryptQR (input) {
    var input = input.substring(2);
    var my_hexdatas = input.split("0x");
    var versionlen = 32;
    var allbits = "";
    for (var i = 0; i < my_hexdatas.length - 1; i++) {
        var my_hexdata = my_hexdatas [i];
        var strp = parseInt(my_hexdata,16).toString(2);
        var toappendlist = '';
        for (var k = 0; k < 32 - strp.length; k++) {
            toappendlist+='0';
        }
        var strp = toappendlist + strp;
        allbits += strp;
    }
    var lastblocklen = versionlen * versionlen - 32 * (my_hexdatas.length - 1);
    var lastblockhex = my_hexdatas [my_hexdatas.length - 1];
    var strp = parseInt(lastblockhex,16).toString(2);
    var lasttoappendlist = '';
    for (var k = 0; k < lastblocklen - strp.length; k++) {
        toappendlist+='0';
    }
    var strp = lasttoappendlist + strp;
    allbits += strp;

    var QRstrs = allbits.match(/.{1,32}/g);

    var QRcode = new Array();
    for (var j = 0; j < QRstrs.length; j++) {
        var QRarray = QRstrs[j].match(/.{1,1}/g);
        QRcode.push (QRarray);
    }
    var twoDMap=[[1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0],
        [1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0],
        [0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
        [0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0],
        [1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1],
        [1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0],
        [0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0],
        [1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 1],
        [0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0],
        [0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0],
        [0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0],
        [0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0],
        [1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1],
        [0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1],
        [0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0],
        [0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0],
        [0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0],
        [0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0],
        [1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0],
        [1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1],
        [1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1],
        [1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0],
        [1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1],
        [1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0],
        [0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0],
        [0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1],
        [1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1],
        [0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0],
        [0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1],
        [0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1],
        [1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1],
        [1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1]]

    var bits = new Array();
    var clen = QRcode.length;

    for(var i =0; i < clen*clen;++i){
        var row = Math.floor(i/clen);
        var col = i%clen;
        bits.push(twoDMap[row][col]^parseInt(QRcode[row][col]));
    }

    QRcode = new Array();
    var i,j,chunk = 32;
    for (i=0,j=bits.length; i<j; i+=chunk) {
        QRcode.push(bits.slice(i,i+chunk));
    }
    return QRcode;
}

function decode(msg) {
    var res1 = decryptQR (msg);
    return loopMatrix (res1);
}

// encode-------
var n1 = parseInt('11101100', 2);
var n2 = parseInt('00010001', 2);
function get_next_encode (arr, x, y, size) {
    var tmp;
    if (shift_up.includes(y)) {
        tmp = x - 1;
        while (tmp >= 0 && arr [tmp] [y + 1] !== blank) {
            tmp = tmp - 1;
        }
        if (tmp < 0) {
            y = y - 1;
            if (arr [x] [y] !== blank) {
                y = y - 1;
            }
            return [x, y];
        }
        else {
            return [tmp, y + 1];
        }
    }
    if (shift_down.includes(y)) {
        tmp = x + 1;
        while (tmp < size && arr [tmp] [y + 1] !== blank) {
            tmp = tmp + 1;
        }
        if (tmp === size) {
            y = y - 1;
            if (y === 8) {
                return [size - 9, y];
            }
            if (arr [x] [y] !== blank) {
                y = y - 1;
            }
            return [x, y];
        }
        else {
            return [tmp, y + 1];
        }
    }
    return [x, y - 1];
}

function get_qr_graph_encode (payload, size) {
    var arr = new Array(size);
    for (var i = 0; i < arr.length; i++) {
        arr[i] = new Array(size);
        for (var j = 0; j < size; j++) {
            arr[i][j] = blank;
        }
    }
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
            arr [i] [j] = position [i] [j];
            arr [i] [(size - j) - 1] = position [i] [j];
            arr [(size - i) - 1] [j] = position [i] [j];
        }
    }
    var align = [[1, 1, 1, 1, 1],
        [1, 0, 0, 0, 1],
        [1, 0, 1, 0, 1],
        [1, 0, 0, 0, 1],
        [1, 1, 1, 1, 1]];
    for (var i = 0; i < 8; i++) {
        arr [i] [8] = 0;
        arr [(size - 1) - i] [8] = 0;
    }
    if (size === 25) {
        for (var i = 0; i < 5; i++) {
            for (var j = 0; j < 5; j++) {
                arr [16 + i] [16 + j] = align [i] [j];
            }
        }
    }
    for (var i = 0; i < size - 8 * 2; i++) {
        arr [6] [8 + i] = (i + 1) % 2;
        arr [8 + i] [6] = (i + 1) % 2;
    }
    var px = size - 1;
    var py = size - 1;
    var __left0__;
    for (var data of payload) {
        for (var i = 0; i < 8; i++) {
            arr [px] [py] = (data >> 7 - i) & 1;
            __left0__ = get_next_encode (arr, px, py, size);
            px = __left0__ [0];
            py = __left0__ [1];
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
            arr [px] [py] = (n1 >> 7 - i) & 1;
            __left0__ = get_next_encode (arr, px, py, size);
            px = __left0__ [0];
            py = __left0__ [1];
        }
        for (var i = 0; i < 8; i++) {
            if (py < 0) {
                break;
            }
            arr [px] [py] = (n2 >> 7 - i) & 1;
            __left0__ = get_next_encode (arr, px, py, size);
            px = __left0__ [0];
            py = __left0__ [1];
        }
    }
    for (var i = 0; i < size; i++) {
        for (var j = 0; j < size; j++) {
            if (arr [i] [j] === blank) {
                arr [i] [j] = 0;
            }
        }
    }
    return arr;
}

function generateintarray (message) {
    var cleanmsg = '';
    for (var i=0; i<message.length; i++) {
        if (message.charCodeAt(i) < 128) {
            cleanmsg += message.charAt(i);
        }
    }
    var result = [];
    result.push(cleanmsg.length);
    for (var i=0; i<cleanmsg.length; i++) {
        result.push(message.charCodeAt(i));
        result.push(message.charCodeAt(i).toString(2).match(/1/g).length % 2);
    }
    if (result [0] <= 14) {
        return [21, result];
    }
    return [25, result];
}

function bits2hex(bits) {
    var result = "";

    var toaddlen = 32 - bits.length % 32;
    var toappendlist = new Array();
    var before = bits.slice(0,32 * Math.floor(bits.length / 32))
    for (var i=0; i<toaddlen; i++){
        toappendlist.push(0);
    }
    before = before.concat(toappendlist);
    var after = bits.slice(32 * Math.floor(bits.length / 32));
    bits = before.concat(after);



    var i,j,chunk = 4;
    var fourbitsl = new Array();
    for (i=0,j=bits.length; i<j; i+=chunk) {
        fourbitsl.push(bits.slice(i,i+chunk));
    }

    var fourbitsllen = fourbitsl.length;
    var startzero = 1;
    for (var m = 0; m < fourbitsllen;m++){
        if (m%8===0){
            result += "0x";
            startzero = 1;
        }
        var hexvalue = 0;
        var base = 1;
        for (var k = fourbitsl[m].length-1; k > -1; k--) {
            hexvalue += base * fourbitsl[m] [k];
            base *= 2;
        }
        if (hexvalue !== 0){
            startzero = 0;
            result+=hexvalue.toString(16);
        }
        if (hexvalue === 0 && startzero === 0){
            result+=hexvalue.toString(16);
        }
    }
    return result.replace("0x0x","0x00x");
}

function getmapReverseBits(map) {
    var bits = new Array();
    for (var i = 0; i < map.length; i++){
        var x = Math.floor(map[i]);
        var count = 0;
        while (x > 0) {
            bits.push(x%2);
            x = Math.floor (x / 2);
            count++;
        }
        while (count < 8) {
            bits.push(0);
            count++;
        }
    }
    return bits;
}


function encode (message) {
    var r1 = generateintarray (message);
    // console.log(r1);
    var size = r1 [0];
    var arr = r1 [1];
    var r2 = get_qr_graph_encode (arr, size);
    // console.log(r2);
    var encoded = encryptQR (size, r2);
    return encoded;
}


function doXor(map, QRcode) {
    var rbits = getmapReverseBits (map);
    var bits = new Array();
    var clen = QRcode.length;
    for (var i = 0; i < clen * clen; i++) {
        var row = Math.floor (i / clen);
        var col = i%clen;
        bits.push (rbits [i] ^ QRcode [row] [col]);
    }
    return bits;
}


function formBaseMap(map) {
    var mlen = map.length;
    map [0] = 0.1;
    var r = 4.0;
    for (var i = 1; i < mlen; i++) {
        map [i] = (r * map [i - 1]) * (1 - map [i - 1]);
    }
}


function formMap(map) {
    formBaseMap (map);
    var mlen = map.length;
    for (var i = 0; i < mlen; i++) {
        map [i] = (Math.floor(map [i] * 255)).toFixed(1);
    }
}


function encryptQR(sidelen, QRcode) {
    var maplen = Math.floor ((sidelen * sidelen) / 8) + 1;
    var map = new Array();
    for (var i=0;i<maplen;++i){
        map.push(0);
    }
    formMap (map);
    var bits = doXor (map, QRcode);
    var result = bits2hex (bits);
    return result;
}

// server-------

function getq(query) {
    //var query = window.location.search.substring(1);
    var vars = query.split('&');
    var type, data;
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split('=');
        if (pair[0] === 'type') {
            type = pair[1]
        } else {
            data = pair[1]
        }
    }
    return [type, data];
}

const server = http.createServer((req, res) => {
    var q = url.parse(req.url);
    var qdata = getq(q.query);
    var data = qdata[1], type = qdata[0];
    var str = '';
    if (type === 'decode') {
        str = decode(data);
    } else if (type === 'encode') {
        str = encode(data)
    }
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/plain');
    res.end(str);
});

server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});
