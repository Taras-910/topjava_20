function setEnable(id, element) {
    var enabled = element.is(":checked");
    $.post({
        url: context.ajaxUrl + id,
        data: "enabled=" + enabled
    }).done(function() {
        element.closest("tr").attr('data-userEnabled', enabled);
        enabled ? successNoty("Set Enabled") : successNoty("Set Disabled");
    }).fail(function (jqXHR, textStatus, errorThrown) {
        $("#id").prop("checked", !enabled);
        failNoty(jqXHR);
    });
}

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
