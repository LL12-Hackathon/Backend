$(document).ready(function() {
    $('#show-entry-form').click(function() {
        $('#entry-form').toggle();
        clearForm();
    });

    loadEntries(); // 페이지 로드 시 일기 목록 불러오기
});

function loadEntries() {
    $.get('/api/diary', function(data) {
        $('#entries').empty();
        if (data.length === 0) {
            $('#entries').append('<p>작성된 일기가 없습니다.</p>');
        } else {
            data.forEach(entry => {
                $('#entries').append(`
                    <div id="entry-${entry.id}">
                        <h3>${entry.title}</h3>
                        <p>${entry.content}</p>
                        <p>${entry.date}</p>
                        <button onclick="editEntry(${entry.id})">수정</button>
                        <button class="delete-button" onclick="deleteEntry(${entry.id})">삭제</button>
                    </div>
                `);
            });
        }
    }).fail(function() {
        $('#entries').append('<p>일기를 불러오는 중 오류가 발생했습니다.</p>');
    });
}

function saveEntry() {
    const entry = {
        date: $('#entry-date').val(),
        title: $('#entry-title').val(),
        content: $('#entry-content').val()
    };
    const id = $('#entry-id').val();

    if (id) {
        $.ajax({
            url: `/api/diary/${id}`,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(entry),
            success: function() {
                loadEntries();
                $('#entry-form').hide();
            },
            error: function() {
                alert('일기 저장 중 오류가 발생했습니다.');
            }
        });
    } else {
        $.ajax({
            url: '/api/diary',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(entry),
            success: function() {
                loadEntries();
                $('#entry-form').hide();
            },
            error: function() {
                alert('일기 저장 중 오류가 발생했습니다.');
            }
        });
    }
}

function editEntry(id) {
    $.get(`/api/diary/${id}`, function(entry) {
        $('#entry-id').val(entry.id);
        $('#entry-date').val(entry.date);
        $('#entry-title').val(entry.title);
        $('#entry-content').val(entry.content);
        $('#entry-form').show();
    }).fail(function() {
        alert('일기를 불러오는 중 오류가 발생했습니다.');
    });
}

function deleteEntry(id) {
    $.ajax({
        url: `/api/diary/${id}`,
        type: 'DELETE',
        success: function() {
            loadEntries();
        },
        error: function() {
            alert('일기 삭제 중 오류가 발생했습니다.');
        }
    });
}

function clearForm() {
    $('#entry-id').val('');
    $('#entry-date').val('');
    $('#entry-title').val('');
    $('#entry-content').val('');
}
