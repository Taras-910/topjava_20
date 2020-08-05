function filteredTable() {
//    debugger;
    $.ajax({
        type: "GET",
        url: context.ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(function(data) {
        context.datatableApi.clear().rows.add(data).draw();
        successNoty("Filtered");
    });
}

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
                },
                {
                    "data": "description",
                },
                {
                    "data": "calories",
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
        })
    });
});
