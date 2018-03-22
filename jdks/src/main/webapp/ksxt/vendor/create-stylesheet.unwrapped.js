var _stylesheet = {};
_stylesheet.ignoreKB262161 = false;

function insertEmptyStyleBefore(node, callback) {
    var style = document.createElement('style');
    style.setAttribute('type', 'text/css');
    var head = document.getElementsByTagName('head')[0];
    if (node) {
        head.insertBefore(style, node);
    } else {
        head.appendChild(style);
    }
    if (style.styleSheet && style.styleSheet.disabled) {
        head.removeChild(style);
        callback('Unable to add any more stylesheets because you have exceeded the maximum allowable stylesheets. See KB262161 for more information.');
    } else {
        callback(null, style);
    }
}

function setStyleCss(style, css, callback) {
    try {
        if (style.styleSheet) {
            style.styleSheet.cssText = css;
        } else if ('textContent' in style) {
            style.textContent = css;
        } else {
            style.appendChild(document.createTextNode(css));
        }
    } catch (e) {
        return callback(e);
    }
    return callback(null);
}

function removeStyleSheet(node) {
    if (node.tagName === 'STYLE' && node.parentNode) {
        node.parentNode.removeChild(node);
    }
}

function createStyleSheet(options, callback) {
    if (!_stylesheet.ignoreKB262161 && document.styleSheets.length >= 31) {
        callback('Unable to add any more stylesheets because you have exceeded the maximum allowable stylesheets. See KB262161 for more information.');
    }

    insertEmptyStyleBefore(options.replace ? options.replace.nextSibling : options.insertBefore, function (err, style) {
        if (err) {
            callback(err);
        } else {
            setStyleCss(style, options.css || "", function (err) {
                if (err) {
                    removeStyleSheet(style);
                    callback(err);
                } else {
                    if (options.replace) {
                        removeStyleSheet(options.replace);
                    }
                    callback(null, style);
                }
            });
        }
    });
}
_stylesheet = {
    appendStyleSheet: function (css, callback) {
        createStyleSheet({
            css: css
        }, callback);
    },
    insertStyleSheetBefore: function (node, css, callback) {
        createStyleSheet({
            insertBefore: node,
            css: css
        }, callback);
    },
    replaceStyleSheet: function (node, css, callback) {
        createStyleSheet({
            replace: node,
            css: css
        }, callback);
    },
    removeStyleSheet: removeStyleSheet
};