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

//data.excess = undefined;
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
