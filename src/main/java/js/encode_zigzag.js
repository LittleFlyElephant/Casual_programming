
var shift_up = [2, 7, 11, 15, 19, 23];
var shift_down = [0, 4, 9, 13, 17, 21];
var blank = 2;
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
    console.log(r1);
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

console.log(encode("CC Team"));