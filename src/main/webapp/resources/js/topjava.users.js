function setEnable(id, enabled) {
//    debugger;
    $.post({
        url: context.ajaxUrl + id,
        data: "enabled=" + enabled
    }).done(function() {
        updateTable();
        successNoty("Set Enabled");
    });
}

// $(document).ready(function () {
$(function () {
    makeEditable({
        ajaxUrl: "admin/users/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        }),
    });
});
