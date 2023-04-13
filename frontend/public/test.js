//index번호
//제목
//이름
//조회수
//작성일
//idx, list, title, name, hit, date
var jsonList=[
{"idx" : 1, "list" : "1", "title" : "악재, 그리고 예고된 악재… 물가·주가·금리의 악몽 [뉴스+]", "name" : "세계일보", "hit" : 1, "date" : "2022.09.14" },
{"idx" : 2, "list" : "2", "title" : "전여옥, ‘광우병 사태’ 김규리 직격…“美 소고기 먹느니 청산가리 먹겠다 망언”", "name" : "디지털타임스", "hit" : 2, "date" : "2022.09.13" },
{"idx" : 3, "list" : "3", "title" : "무이파는 중국, 므르복은 일본… 난마돌은 19일 제주도 접근할 듯", "name" : "부산일보", "hit" : 100, "date" : "2022.09.10" },
{"idx" : 4, "list" : "4", "title" : "[단독] ‘싱글맘’ 김나영, 역삼동 99억 건물주 됐다", "name" : "매일경제", "hit" : 1000, "date" : "2022.09.09" },
{"idx" : 5, "list" : "5", "title" : "尹정부, 부울경 경자구역 추가지정 검토…신산업 유도", "name" : "국제신문", "hit" : 1001, "date" : "2022.08.09" },
{"idx" : 6, "list" : "6", "title" : "케이뱅크, 파킹통장 금리 연 2.3%로 인상", "name" : "파이낸셜뉴스", "hit" : 9999, "date" : "2021.05.09" },
{"idx" : 7, "list" : "7", "title" : "충주시, '예술하라'아트페어에서 온. 오프라인 열려", "name" : "대전일보", "hit" : 888, "date" : "2020.09.10" }
];
 
var keyName=["idx", "list", "title", "name", "hit",  "date"];
 
 
$().ready(function () {
    // 하단 "목록 더보기" 클릭 시 3개씩 로드
    $("#btnNext").on("click", function () {    //더 보기 버튼
        var $tbody = $("#listArea");
        var $tr;
        var $td; 
        var key;
        var listCnt =$("#listArea tr").length;                           //* table의 길이 구하기
        var jsonRowCnt = jsonList.length;                               //마지막 행에 대한 출력 제어를 하기 위함.
        var j = listCnt;
 
        while( j < listCnt+3 && j < jsonRowCnt ){
            console.log("[출력] " + j);
            $tr = $("<tr></tr>");
            for (var i = 0; i < 6; ++i) {
                $td = $("<td></td>");     //td      선언부 위치가 중요함..
                key = keyName[i];
                
                if (i == 0) {
                    var $chk = $("<input />");
                    $chk.attr("type", "checkbox");
                    $chk.attr("name", "idx");
                    $chk.attr("value",  jsonList[j][key] );
                    $td.append($chk);
                } else {
                    $td.text(jsonList[j][key]);
                }
                $tr.append($td);
            }
            $delSpan = $("<span>del</span>");
            //=======================del 클릭을 통해 행 삭제====
            $delSpan.on("click", function(){
 
                var chk = $(this).parents("td").parents("tr").children("td:first-child").children("input").prop("checked");
                var idx =  $(this).parents("td").parents("tr").children("td:first-child").children("input").val();
 
                if( chk ){
                    $(this).parents("td").parents("tr").remove();
                }
                else{
                    alert("삭제할 행"+idx+"의 체크박스를 선택하지 않았습니다.");
                }
 
            });//e:$delSpan.on("click")
            //==================================================
            $td = $("<td></td>");
            $td.append($delSpan);
            $tr.append($td);
            $tbody.append($tr);
            ++j;
        }
    });//e:$("#btnNext").on("click")
 
    $("#chkAll").on("click", function (){
        console.log($("[name='idx']").val());
        console.log($("[name='idx']:eq(0)").val());
 
        ///한꺼번에
        $("[name='idx']").prop("checked",this.checked);
    });//e:$("#chkAll").on("click")
 
    
    // 하단 "선택삭제" 클릭 시 체크박스에 체크된 목록만 삭제
    $("#btnDel").on("click", function(){
        var cnt = $("#listArea tr").length;
        var $list = $("#listArea tr");
 
        if(  $("[name='idx']:checked").length == 0 ){
            alert("선택된 항목이 없습니다.");
            return;
        }
        for ( var i = 0 ; i < cnt; ++i ){
            var $chkB = $list.eq(i).children("td:first-child").children("input");
            if( $chkB.prop("checked") ){
                $list.eq(i).remove();
            }
        }
    });//e:$("#btnDel").on("click")
});//$().ready()