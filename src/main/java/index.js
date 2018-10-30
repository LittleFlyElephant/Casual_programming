function getSubset(set, subsets, x, cur) {
    if (set.length === x) {
        subsets.push(cur.slice());
        return;
    }
    getSubset(set, subsets, x+1, cur);
    var next = cur.slice();
    next.push(set[x]);
    getSubset(set, subsets, x+1, next);
}

var sub = [];
getSubset([1,2,3], sub, 0, []);
console.log(sub);

// exports.helloWorld = function helloWorld (req, res) {
//     if (req.body.message === undefined) {
//         res.status(400)
//             .json({error :'No message defined!'});
//     } else {
//         // Everything is ok
//         console.log(req.body.message);
//
//         res.status(200)
//             .json({status: 'OK!'})
//             .end();
//     }
// };