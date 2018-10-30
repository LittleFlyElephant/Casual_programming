// import {decryptQR} from "./decode";

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
        console.log(strp);
        allbits += strp;
    }
    var lastblocklen = versionlen * versionlen - 32 * (my_hexdatas.length - 1);
    var lastblockhex = my_hexdatas [my_hexdatas.length - 1];
    var strp = parseInt(lastblockhex,16).toString(2);
    var lasttoappendlist = '';
    for (var k = 0; k < lastblocklen - strp.length; k++) {
        lasttoappendlist+='0';
    }
    var strp = lasttoappendlist + strp;
    console.log(strp);
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


console.log(decode("0x2b23d6830x15a0de0d0x744784010x29e880700xfe1adf5c0xb96061290x1127b67c0x311690430xc63153140xf6e00650x92d3960b0xf59a79070x704e73d40x977fd8090xf516e98a0x3e0c19f10xac626d040x6a3e58650xca85aa3e0x6266b640x842ddcb40x4e7c879c0x85dd21240x3afae3dc0xe07908a70x664685970xb38246f70x511908330x40a111ee0xc12c8fd10x82984c520x4ddee6f6"));