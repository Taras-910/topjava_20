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

$('.modal-body#dateTime').each(function (isoDate) {
    return isoDate.replace('T', ' ').substr(0, 16);
});

jQuery(function(){
    jQuery('#startDate').datetimepicker({
        format:'Y-m-d',
        formatDate:'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                maxDate:jQuery('#endDate').val()?jQuery('#endDate').val():false
            })
        },
        timepicker:false
    });
    jQuery('#endDate').datetimepicker({
        format:'Y-m-d',
        formatDate:'Y-m-d',
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
                maxDate:jQuery('#endTime').val()?jQuery('#endTime').val():false
            })
        },
        datepicker:false
    });
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
            ],
            "aoColumnDefs" : [
                {
                    'bSortable': false,
                    'aTargets': [ 3,4 ],
                }]
            ,
            "createdRow": function (row, data, dataIndex) {
                $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: updateFilteredTable
    });
});
