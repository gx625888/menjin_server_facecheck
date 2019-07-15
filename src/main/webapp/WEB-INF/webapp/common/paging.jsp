<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><div class="page">
	<table width="100%" border="0" cellpadding="0" cellspacing="5">
		<tr>
			<td align="right">&nbsp;当前是第${pages.currentPage}页&nbsp; 
				<c:if test="${pages.currentPage!=1}">
					<a style="cursor: pointer;" onclick="toPage(0)">首页</a>&nbsp;
					<a style="cursor: pointer;" onclick="toPage('${pages.priviousPage}')">上一页</a>&nbsp;
				</c:if> 
				<c:if test="${pages.totalPage>pages.currentPage}">
					<a style="cursor: pointer;" onclick="toPage('${pages.nextPage}')">下一页</a>&nbsp;
					<a style="cursor: pointer;" onclick="toPage('${pages.totalPage}')">末页</a>&nbsp;
				</c:if> 共${pages.totalCount}条&nbsp; 共${pages.totalPage}页&nbsp;
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
function toPage(obj){

	if(obj){
		$('input[name="currentPage"]').val(obj);
	}
	$('#pageCondition').submit();
	return false;
}
</script>