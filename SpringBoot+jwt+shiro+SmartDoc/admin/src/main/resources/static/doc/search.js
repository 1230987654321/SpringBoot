let api = [];
api.push({
    alias: 'api',
    order: '1',
    desc: '管理员',
    link: '管理员',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '添加管理员',
});
api[0].list.push({
    order: '2',
    desc: '查询管理员列表',
});
api[0].list.push({
    order: '3',
    desc: '根据id查询管理员',
});
api[0].list.push({
    order: '4',
    desc: '通过token获取登录用户信息',
});
api[0].list.push({
    order: '5',
    desc: '修改管理员状态',
});
api[0].list.push({
    order: '6',
    desc: '修改管理员密码',
});
api[0].list.push({
    order: '7',
    desc: '修改管理员信息',
});
api[0].list.push({
    order: '8',
    desc: '删除管理员',
});
api.push({
    alias: 'AdminLogController',
    order: '2',
    desc: '管理员操作日志',
    link: '管理员操作日志',
    list: []
})
api.push({
    alias: 'BannerController',
    order: '3',
    desc: '轮播',
    link: '轮播',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '添加轮播图',
});
api[2].list.push({
    order: '2',
    desc: '查询轮播图详情',
});
api[2].list.push({
    order: '3',
    desc: '查询轮播图列表',
});
api[2].list.push({
    order: '4',
    desc: '修改轮播图状态',
});
api[2].list.push({
    order: '5',
    desc: '修改轮播图',
});
api[2].list.push({
    order: '6',
    desc: '删除轮播图',
});
api.push({
    alias: 'ConfigController',
    order: '4',
    desc: '基本配置',
    link: '基本配置',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '查询配置',
});
api[3].list.push({
    order: '2',
    desc: '修改配置',
});
api.push({
    alias: 'LoginController',
    order: '5',
    desc: '轮登录控制器',
    link: '轮登录控制器',
    list: []
})
api[4].list.push({
    order: '1',
    desc: '登录',
});
api[4].list.push({
    order: '2',
    desc: '注销',
});
api[4].list.push({
    order: '3',
    desc: '验证角色与权限',
});
api.push({
    alias: 'MenuController',
    order: '6',
    desc: '左侧菜单栏',
    link: '左侧菜单栏',
    list: []
})
api[5].list.push({
    order: '1',
    desc: '查询所有菜单栏',
});
api[5].list.push({
    order: '2',
    desc: '根据id查询菜单栏',
});
api[5].list.push({
    order: '3',
    desc: '添加菜单栏',
});
api[5].list.push({
    order: '4',
    desc: '修改菜单栏',
});
api[5].list.push({
    order: '5',
    desc: '修改菜单栏是否隐藏',
});
api[5].list.push({
    order: '6',
    desc: '删除菜单栏',
});
api.push({
    alias: 'PictureController',
    order: '7',
    desc: '图片表',
    link: '图片表',
    list: []
})
api[6].list.push({
    order: '1',
    desc: '上传图片',
});
api[6].list.push({
    order: '2',
    desc: '删除图片',
});
api[6].list.push({
    order: '3',
    desc: '查询图片',
});
api[6].list.push({
    order: '4',
    desc: '查询图片列表',
});
api.push({
    alias: 'RoleController',
    order: '8',
    desc: '角色',
    link: '角色',
    list: []
})
api[7].list.push({
    order: '1',
    desc: '添加角色',
});
api[7].list.push({
    order: '2',
    desc: '修改角色',
});
api[7].list.push({
    order: '3',
    desc: '删除角色',
});
api[7].list.push({
    order: '4',
    desc: '获取角色列表',
});
api[7].list.push({
    order: '5',
    desc: '获取角色详情',
});
api.push({
    alias: 'UserController',
    order: '9',
    desc: '用户',
    link: '用户',
    list: []
})
api[8].list.push({
    order: '1',
    desc: '查询用户列表',
});
api[8].list.push({
    order: '2',
    desc: '查询用户详情',
});
document.onkeydown = keyDownSearch;

function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code === 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                searchArr.push({
                    order: apiData.order,
                    desc: apiData.desc,
                    link: apiData.link,
                    alias: apiData.alias,
                    list: apiData.list
                });
            } else {
                let methodList = apiData.list || [];
                let methodListTemp = [];
                for (let j = 0; j < methodList.length; j++) {
                    const methodData = methodList[j];
                    const methodDesc = methodData.desc;
                    if (methodDesc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                        methodListTemp.push(methodData);
                        break;
                    }
                }
                if (methodListTemp.length > 0) {
                    const data = {
                        order: apiData.order,
                        desc: apiData.desc,
                        alias: apiData.alias,
                        link: apiData.link,
                        list: methodListTemp
                    };
                    searchArr.push(data);
                }
            }
        }
        let html;
        if (searchValue === '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api, liClass, display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr, liClass, display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            let $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiData, liClass, display) {
    let html = "";
    if (apiData.length > 0) {
        for (let j = 0; j < apiData.length; j++) {
            html += '<li class="' + liClass + '">';
            html += '<a class="dd" href="' + apiData[j].alias + '.html#header">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="' + display + '">';
            let doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="' + apiData[j].alias + '.html#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}