/*
 * description: ui/js/app/article/list.js 文章栏目js
 */
	
	/**
	 * jgGrid 分页组件
	 */
	var JqGrid,
		winWidth =  '1000px',
		winHeight = '500px',
		addTitle = "添加文章",
		addUrl =  "/modules/article/add",
		editTitle = "编辑文章",
		editUrl =  "/modules/article/edit",
		deleteUrl = "/modules/article/delete",
		jqGridId = "#table_jq",
		jqPageId = "#pager_jq",
		dataUrl = "/modules/article/getDataByAjax";
	$.jgrid.defaults.styleUI = "Bootstrap";
	JqGrid = $(jqGridId).jqGrid({
		url : dataUrl,
		datatype : "json",
		colNames : [ '栏目', '标题', '权重', '发布者', '更新时间','操作' ],
		colModel : [
			{
				name : 'cname',
				index : 'cname',
				width:'100'
			},
			{
				name : 'title',
				index : 'title',
				width:'180'
			},
			{
				name : 'weight',
				index : 'weight',
				width:'80'
			},
			{
				name : 'createby',
				index : 'createby',
				width:'80'
			},
			{
				name : 'updatedate',
				index : 'updatedate',
				width:'120'
			},
			{
				name : 'tax',
				index : 'tax',
				width:'80',
				formatter : function(cellvalue,options,rowObject) {
					var str = '';
					str += '<a style="margin-bottom:0px;" class="btn btn-white btn-xs btn-bitbucket" title="编辑" onclick="hm_fn_editFun(editTitle,winWidth,winHeight,editUrl,'+"'"+rowObject.ids+"'"+',winname)"><i class="fa fa-edit"></i></a>&nbsp;';
					str += '<a style="margin-bottom:0px;" class="btn btn-white btn-xs btn-bitbucket" title="删除" onclick="hm_fn_del(jqGridId,dataUrl,deleteUrl,'+"'"+rowObject.ids+"'"+')"><i class="fa fa-trash"></i></a>';
					return str;
				}
			} ],
	autowidth : true,
	height : 550,
	rowNum : 20,
	rowList : [20,30,50],
	pager : jqPageId,
	mtype : "post",
	viewrecords : true,
	jsonReader : {  
		root:"list",  
		page: "page",  
		total: "totalPage",  
		records: "totalRow",  
		repeatitems: false,  
		id: "0"  
		}
	});

	var timeoutHnd;
	var flAuto = false;
	function doSearch(ev) {
	  if (!flAuto)
	    return;
	  if (timeoutHnd)
	    clearTimeout(timeoutHnd);
	    timeoutHnd = setTimeout(gridReload, 500);
	}
	function gridReload() {
	  var search_val = jQuery("#search-input").val()||"";
	  $(jqGridId).jqGrid('setGridParam', {
		  url : dataUrl+"?search_val=" + search_val,
		  page : 1
	  }).trigger("reloadGrid");
	}
	function enableAutosubmit(state) {
	  flAuto = state;
	  jQuery("#search-btn").attr("disabled", state);
	}
	
	/**
	 * ztree设置
	 */
	var setting = {
			view: {
				selectedMulti: false
			},
			data: {
				key: {
					title:"t"
			},
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: beforeClick,
			beforeCollapse: beforeCollapse,
			beforeExpand: beforeExpand,
			onCollapse: onCollapse,
			onExpand: onExpand
		}
	};
	
	var zNodes;
	$.ajax({
		   type: "POST",
		   async:false,
		   url: "/modules/category/getCategoryZtreeData",
		   success: function(data){
		     zNodes = data;
		   }
		});
	
	var log, className = "dark";
	function beforeClick(treeId, treeNode) {
		if (treeNode.isParent) {
			return true;
		} else {
			$(jqGridId).jqGrid('setGridParam', {
				  url : dataUrl+"?cids="+treeNode.id,
				  page : 1
			  }).trigger("reloadGrid");
			return true;
		}
	}
	function beforeCollapse(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		showLog("[ "+getTime()+" beforeCollapse ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
		return (treeNode.collapse !== false);
	}
	function onCollapse(event, treeId, treeNode) {
		showLog("[ "+getTime()+" onCollapse ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name);
	}		
	function beforeExpand(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		showLog("[ "+getTime()+" beforeExpand ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
		return (treeNode.expand !== false);
	}
	function onExpand(event, treeId, treeNode) {
		showLog("[ "+getTime()+" onExpand ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name);
	}
	function showLog(str) {
		if (!log) log = $("#log");
		log.append("<li class='"+className+"'>"+str+"</li>");
		if(log.children("li").length > 8) {
			log.get(0).removeChild(log.children("li")[0]);
		}
	}
	function getTime() {
		var now= new Date(),
		h=now.getHours(),
		m=now.getMinutes(),
		s=now.getSeconds(),
		ms=now.getMilliseconds();
		return (h+":"+m+":"+s+ " " +ms);
	}
	
	function expandNode(e) {
		var zTree = $.fn.zTree.getZTreeObj("CategoryZtree"),
		type = e.data.type,
		nodes = zTree.getSelectedNodes();
		if (type.indexOf("All")<0 && nodes.length == 0) {
			alert("请先选择一个父节点");
		}
	
		if (type == "expandAll") {
			zTree.expandAll(true);
		} else if (type == "collapseAll") {
			zTree.expandAll(false);
		} else {
			var callbackFlag = $("#callbackTrigger").attr("checked");
			for (var i=0, l=nodes.length; i<l; i++) {
				zTree.setting.view.fontCss = {};
				if (type == "expand") {
					zTree.expandNode(nodes[i], true, null, null, callbackFlag);
				} else if (type == "collapse") {
					zTree.expandNode(nodes[i], false, null, null, callbackFlag);
				} else if (type == "toggle") {
					zTree.expandNode(nodes[i], null, null, null, callbackFlag);
				} else if (type == "expandSon") {
					zTree.expandNode(nodes[i], true, true, null, callbackFlag);
				} else if (type == "collapseSon") {
					zTree.expandNode(nodes[i], false, true, null, callbackFlag);
				}
			}
		}
	}
	$(document).ready(function(){
		$.fn.zTree.init($("#CategoryZtree"), setting, zNodes);
		$("#expandBtn").bind("click", {type:"expand"}, expandNode);
		$("#collapseBtn").bind("click", {type:"collapse"}, expandNode);
		$("#toggleBtn").bind("click", {type:"toggle"}, expandNode);
		$("#expandSonBtn").bind("click", {type:"expandSon"}, expandNode);
		$("#collapseSonBtn").bind("click", {type:"collapseSon"}, expandNode);
		$("#expandAllBtn").bind("click", {type:"expandAll"}, expandNode);
		$("#collapseAllBtn").bind("click", {type:"collapseAll"}, expandNode);
	});