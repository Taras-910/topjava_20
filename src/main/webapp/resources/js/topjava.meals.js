var mealAjaxUrl = "profile/meals/";
function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mealAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$('#dateTime').datetimepicker({
    format: 'Y-m-d\\TH:i'
});

jQuery(function(){
    jQuery('#startDate').datetimepicker({
        format:'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                startDate:jQuery('#endDate').val()?jQuery('#endDate').val():false
            })
        },
        timepicker:false
    });
});

jQuery(function(){
    jQuery('#endDate').datetimepicker({
        format:'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                minDate:jQuery('#startDate').val()?jQuery('#startDate').val():false
            })
        },
        timepicker:false
    });
});

jQuery(function(){
    jQuery('#startTime').datetimepicker({
        format:'H:i',
        onShow:function( ct ){
            this.setOptions({
                startDate:jQuery('#endTime').val()?jQuery('#endTime').val():false
            })
        },
        datepicker:false
    });
});

jQuery(function(){
    jQuery('#endTime').datetimepicker({
        format:'H:i',
        onShow:function( ct ){
            this.setOptions({
                minDate:jQuery('#startTime').val()?jQuery('#startTime').val():false
            })
        },
        datepicker:false
    });
});

$(function () {
//    debugger;
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        if (type === "display") {
                            return date.substring(0, 16).replace("T", " ");
                        }
                        return date;
                    },
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "ordering": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "Delete",
                    "ordering": false,
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
           ,
            "createdRow": function (row, data, dataIndex) {
                if (!data.excess) {
                    $(row).attr("data-mealExcess", false);
                } else {
                    $(row).attr("data-mealExcess", true);
                }
            }
        }),
        updateTable: updateFilteredTable
    });
});
