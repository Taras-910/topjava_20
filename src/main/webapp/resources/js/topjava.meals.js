// $(document).ready(function () {
$(function () {
    makeEditable({
        ajaxUrl: "meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "defaultContent": ""
                },
                {
                    "data": "description",
                    "defaultContent": ""
                },
                {
                    "data": "calories",
                    "defaultContent": ""
                },
                {
                    "defaultContent": "Edit",
                    "ordering": false
                },
                {
                    "defaultContent": "Delete",
                    "ordering": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        }),
    });
});
