require.config({
	paths: {
		jquery:"lib/jquery.min",
		bootstrap:"lib/bootstrap.min",
		layer:"lib/layer/layer",
		jqGrid:"lib/jqgrid/jquery.jqGrid.min",
		jqGridlocale:"lib/jqgrid/grid.locale-cn",
		ztreecore:"lib/zTree/js/jquery.ztree.core-3.5",
		ztreeexcheck:"lib/zTree/js/jquery.ztree.excheck-3.5",
		ztreeexedit:"lib/zTree/js/jquery.ztree.exedit-3.5",
		kindeditor:"lib/kindeditor/kindeditor",
		kindeditorlang:"lib/kindeditor/lang/zh_CN",
		jqvalidate:"lib/validate/jquery.validate.min",
		jqvalidatemsg:"lib/validate/messages_zh.min",
		webuploader:"lib/webuploader/webuploader.min",
		icheck:"lib/iCheck/icheck.min",
		chosen:"lib/chosen/chosen.jquery",
		clockpicker:"lib/clockpicker/clockpicker",
		colorpicker:"lib/colorpicker/bootstrap-colorpicker.min",
		cropper:"lib/cropper/cropper.min",
		datapicker:"lib/datapicker/bootstrap-datepicker",
		dropzone:"lib/dropzone/dropzone",
		easypiechart:"lib/easypiechart/jquery.easypiechart",
		echarts:"lib/echarts/echarts-all.js",
		ionRangeSlider:"lib/ionRangeSlider/ion.rangeSlider.min",
		knob:"lib/jsKnob/jquery.knob",
		toastr:"lib/toastr/toastr.min",
		metisMenu:"lib/metisMenu/jquery.metisMenu",
		pace:"lib/pace/pace.min",
		slimscroll:"lib/slimscroll/jquery.slimscroll.min",
		
		
		/** 平台自定义js */
		/*hmCommon:"app/common",
		hmCustom:"app/custom",
		hmWebuploaderCustom:"app/webuploader.custom",*/
	},

	shim: {
		'bootstrap': {
	        deps: ['jquery']
	    },
	    'jqueryForm':	{
	        deps: ['jquery']
	    },
	    'layer': {
	        deps: ['jquery']
	    },
	    'jqGrid': {
	        deps: ['jquery']
	    },
	    'jqGridlocale': {
	        deps: ['jquery']
	    },
	    'ztreecore': {
	        deps: ['jquery']
	    },
	    'ztreeexcheck': {
	        deps: ['jquery']
	    },
	    'ztreeexedit':{
	    	deps:['jquery']
	    },
	    'kindeditor': {
	        deps: ['jquery']
	    },
	    'kindeditorlang':{
	        deps: ['jquery']
	    },
	    'jqvalidate':{
	        deps: ['jquery']
	    },
	    'jqvalidatemsg':{
	        deps: ['jquery']
	    },
	    'webuploader':{
	        deps: ['jquery']
	    },
	    'icheck':{
	        deps: ['jquery']
	    },
	    'chosen':{
	        deps: ['jquery']
	    },
	    'clockpicker':{
	        deps: ['jquery']
	    },
	    'colorpicker':{
	        deps: ['jquery']
	    },
	    'cropper':{
	        deps: ['jquery']
	    },
	    'datapicker':{
	        deps: ['jquery']
	    },
	    'dropzone':{
	        deps: ['jquery']
	    },
	    'easypiechart':{
	        deps: ['jquery']
	    },
	    'echarts':{
	        deps: ['jquery']
	    },
	    'ionRangeSlider':{
	        deps: ['jquery']
	    },
	    'knob':{
	        deps: ['jquery']
	    },
	    'toastr':{
	        deps: ['jquery']
	    },
	    'metisMenu':{
	        deps: ['jquery']
	    },
	    'pace':{
	        deps: ['jquery']
	    },
	    'slimscroll':{
	        deps: ['jquery']
	    }

	}
});

/*require(["jquery"],function($){
	alert($("#pager_user").html());
});*/

require(["jquery","jqGrid","ztreecore","ztreeexcheck","ztreeexedit"],function($,jgrid){
	
	/*new common.PopupPage("#GTAddPage",{
		fn:'addFun',
		title:'添加文章',
		width:'1000px',
		height:'600px',
		url:'/modules/article/add'
	});*/
	
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
		deleteUrl = "/modules/article/delete";

//	$.jgrid.defaults.styleUI = "Bootstrap";
	JqGrid = $("#table_user").jqGrid({
		url : "${cxt!}/modules/article/getDataByAjax",
		datatype : "json",
		colNames : [ '栏目', '标题', '权重', '发布者', '更新时间','操作' ],
		colModel : [
				{
					name : 'cname',
					index : 'cname'
				},
				{
					name : 'title',
					index : 'title'
				},
				{
					name : 'weight',
					index : 'weight'
				},
				{
					name : 'createby',
					index : 'createby'
				},
				{
					name : 'updatedate',
					index : 'updatedate'
				},
				{
					name : 'tax',
					index : 'tax',
					width:'80',
					formatter : function(cellvalue,options,rowObject) {
						var str = '';
						str += '<a style="margin-bottom:0px;" class="btn btn-white btn-xs btn-bitbucket" title="编辑" onclick="$.fn.JqGrid.editFun('+rowObject.ids+',winWidth,winHeight,editUrl)"><i class="fa fa-edit"></i></a>&nbsp;';
						str += '<a style="margin-bottom:0px;" class="btn btn-white btn-xs btn-bitbucket" title="删除" onclick="$.fn.JqGrid.deleteFun('+rowObject.ids+',deleteUrl)"><i class="fa fa-trash"></i></a>';
						return str;
					}
				} ],
		autowidth : true,
		height : 550,
		rowNum : 20,
		rowList : [20,30,50],
		pager : '#pager_user',
		mtype : "post",
		viewrecords : true,
		caption : "文章列表",
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
	  $("#table_user").jqGrid('setGridParam', {
	    url : "${cxt!}/modules/article/getDataByAjax?search_val=" + search_val,
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
			   url: "${cxt!}/modules/category/getCategoryZtreeData",
			   success: function(data){
			     zNodes = data;
			   }
			});
		
		var log, className = "dark";
		function beforeClick(treeId, treeNode) {
			if (treeNode.isParent) {
				return true;
			} else {
				alert("这个 Demo 是用来测试 展开 / 折叠 的...\n\n去点击父节点吧... ");
				return false;
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
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
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
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			$("#expandBtn").bind("click", {type:"expand"}, expandNode);
			$("#collapseBtn").bind("click", {type:"collapse"}, expandNode);
			$("#toggleBtn").bind("click", {type:"toggle"}, expandNode);
			$("#expandSonBtn").bind("click", {type:"expandSon"}, expandNode);
			$("#collapseSonBtn").bind("click", {type:"collapseSon"}, expandNode);
			$("#expandAllBtn").bind("click", {type:"expandAll"}, expandNode);
			$("#collapseAllBtn").bind("click", {type:"collapseAll"}, expandNode);
		});
	
	
});
