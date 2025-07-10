<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Leave Agenda</title>
        <link href="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/leave.css">
        <script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.js"></script>
        <style>
            .hidden {
                display: none;
            }
            .view-toggle button {
                background: #e0e7ef;
                color: #34495e;
                border: none;
                border-radius: 8px;
                padding: 10px 24px;
                margin-right: 10px;
                font-size: 1rem;
                font-weight: 500;
                cursor: pointer;
                transition: background 0.2s, color 0.2s;
            }
            .view-toggle button.active {
                background: #27ae60;
                color: #fff;
            }
            .view-toggle button.inactive {
                background: #bfc9d1;
                color: #fff;
            }
            /* Calendar event colors */
            .fc-event-approved {
                background-color: #27ae60 !important;
                border-color: #27ae60 !important;
                color: #fff !important;
            }
            .fc-event-rejected {
                background-color: #e74c3c !important;
                border-color: #e74c3c !important;
                color: #fff !important;
            }
            /* Staff view cell colors */
            .working {
                background-color: #27ae60 !important;
                color: #fff !important;
            }
            .absent {
                background-color: #e74c3c !important;
                color: #fff !important;
            }
            .future {
                background-color: #bfc9d1 !important;
                color: #fff !important;
            }
        </style>
    </head>
    <body>
        <div class="leave-table-container">
            <h1>Leave Agenda</h1>
            <div class="view-toggle" style="margin-bottom: 10px;">
                <button id="calendar-btn" class="active" onclick="switchView('calendar')">Calendar View</button>
                <button id="staff-btn" class="inactive" onclick="switchView('staff')">Staff View</button>
            </div>
            <div class="filter-container">
                <label for="employeeFilter">Filter by Employee Name: </label>
                <input type="text" id="employeeFilter" onkeyup="applyFilter()" placeholder="Enter employee name">
            </div>
            <div style="margin-bottom: 10px;">
                <span class="note-staff-view" style="color: green;">■ Working   </span>
                <span class="note-cal-view" style="color: green;">■ Approved   </span>
                <span class="note-staff-view" style="color: red;">■ Absent   </span>
                <span class="note-cal-view" style="color: red;">■ Rejected</span>
                <span class="note-staff-view" style="color: grey;">■ Future</span>
            </div>
            <div id="calendar" style="display: none;">
                <div class="calendar-nav" style="margin-bottom: 10px;">
                    <label for="monthPicker">Select Month: </label>
                    <input type="month" id="monthPicker" onchange="updateCalendarMonth()">
                </div>
            </div>
            <div id="staff-view" style="display: none;">
                <div class="week-nav" style="display: none;">
                    <button onclick="changeWeek(-1)">Previous Week</button>
                    <button onclick="changeWeek(1)">Next Week</button>
                    <button onclick="goToToday()">Today</button>
                    <label for="weekPicker">Select Week: </label>
                    <input type="date" id="weekPicker" onchange="setWeekFromDate()">
                    <span id="week-range"></span>
                </div>
                <div id="staff-table"></div>
            </div>
            <a href="${pageContext.request.contextPath}/home">Back to Home</a>
        </div>
        <script src="${pageContext.request.contextPath}/js/leave.js"></script>
        <script>
                        let currentWeekStart = new Date();
                        let employeeFilter = '';
                        let calendar;
                        const notesCal = document.querySelectorAll('.note-cal-view');
                        const notesStaff = document.querySelectorAll('.note-staff-view');

                        function showNote(on, off) {
                            on.forEach(el => el.classList.remove('hidden'));
                            off.forEach(el => el.classList.add('hidden'));
                        }

                        // Define switchView globally
                        function switchView(view) {
                            console.log('Switching to view:', view);
                            var calendarEl = document.getElementById('calendar');
                            var staffViewEl = document.getElementById('staff-view');
                            var calendarBtn = document.getElementById('calendar-btn');
                            var staffBtn = document.getElementById('staff-btn');
                            var weekNav = document.querySelector('.week-nav');

                            if (view === 'calendar') {
                                showNote(notesCal, notesStaff);
                                calendarEl.style.display = 'block';
                                staffViewEl.style.display = 'none';
                                calendarBtn.classList.add('active');
                                calendarBtn.classList.remove('inactive');
                                staffBtn.classList.add('inactive');
                                staffBtn.classList.remove('active');
                                weekNav.style.display = 'none';
                                if (calendar)
                                    calendar.render();
                            } else {
                                showNote(notesStaff, notesCal);
                                calendarEl.style.display = 'none';
                                staffViewEl.style.display = 'block';
                                calendarBtn.classList.add('inactive');
                                calendarBtn.classList.remove('active');
                                staffBtn.classList.add('active');
                                staffBtn.classList.remove('inactive');
                                weekNav.style.display = 'block';
                                loadStaffView();
                            }
                        }

                        // Define loadStaffView globally
                        function loadStaffView() {
                            console.log('Loading staff view for week starting:', currentWeekStart);
                            var staffTableEl = document.getElementById('staff-table');
                            var weekRangeEl = document.getElementById('week-range');
                            var startDate = new Date(currentWeekStart);
                            startDate.setDate(startDate.getDate() - startDate.getDay() + (startDate.getDay() === 0 ? -6 : 1)); // Adjust to Monday
                            var endDate = new Date(startDate);
                            endDate.setDate(endDate.getDate() + 6);
                            var today = new Date();
                            today.setHours(0, 0, 0, 0); // Normalize to start of day

                            weekRangeEl.textContent = startDate.toLocaleDateString('en-GB', {day: 'numeric', month: 'short'}) +
                                    ' - ' + endDate.toLocaleDateString('en-GB', {day: 'numeric', month: 'short', year: 'numeric'});

                            let fetchUrl = '${pageContext.request.contextPath}/leave/agenda?action=staff&start=' + startDate.toISOString().split('T')[0];
                            if (employeeFilter)
                                fetchUrl += '&employeeFilter=' + encodeURIComponent(employeeFilter);

                            fetch(fetchUrl)
                                    .then(response => {
                                        if (!response.ok)
                                            throw new Error('Network response was not ok: ' + response.status);
                                        return response.json();
                                    })
                                    .then(data => {
                                        console.log('Staff view data:', data);
                                        if (data.length === 0) {
                                            staffTableEl.innerHTML = '<p>No staff found in your department.</p>';
                                            return;
                                        }
                                        let html = '<table><tr><th>Employee</th>';
                                        let currentDate = new Date(startDate);
                                        while (currentDate <= endDate) {
                                            html += '<th>' + currentDate.toLocaleDateString('en-GB', {weekday: 'short', day: 'numeric', month: 'short'}) + '</th>';
                                            currentDate.setDate(currentDate.getDate() + 1);
                                        }
                                        html += '</tr>';
                                        data.forEach(user => {
                                            if (!employeeFilter || user.fullName.toLowerCase().includes(employeeFilter.toLowerCase())) {
                                                html += '<tr><td>' + user.fullName + '</td>';
                                                currentDate = new Date(startDate);
                                                while (currentDate <= endDate) {
                                                    let dateStr = currentDate.toISOString().split('T')[0];
                                                    let isFuture = currentDate > today;
                                                    let status = isFuture ? 'future' : (user.days.find(day => day.date === dateStr) ? 'absent' : 'working');
                                                    html += '<td class="' + status + '">' + status.charAt(0).toUpperCase() + status.slice(1) + '</td>';
                                                    currentDate.setDate(currentDate.getDate() + 1);
                                                }
                                                html += '</tr>';
                                            }
                                        });
                                        html += '</table>';
                                        staffTableEl.innerHTML = html;
                                    })
                                    .catch(error => {
                                        console.error('Error loading staff view:', error);
                                        staffTableEl.innerHTML = '<p>Error loading staff view. Please try again.</p>';
                                    });
                        }

                        // Change week for staff view
                        function changeWeek(offset) {
                            console.log('Changing week by offset:', offset);
                            currentWeekStart.setDate(currentWeekStart.getDate() + offset * 7);
                            loadStaffView();
                        }

                        // Go to current week
                        function goToToday() {
                            console.log('Going to today');
                            currentWeekStart = new Date();
                            loadStaffView();
                        }

                        // Set week from date picker
                        function setWeekFromDate() {
                            let selectedDate = document.getElementById('weekPicker').value;
                            if (selectedDate) {
                                console.log('Selected week starting:', selectedDate);
                                currentWeekStart = new Date(selectedDate);
                                loadStaffView();
                            }
                        }

                        // Update calendar month from date picker
                        function updateCalendarMonth() {
                            let monthPicker = document.getElementById('monthPicker').value;
                            if (monthPicker && calendar) {
                                console.log('Selected month:', monthPicker);
                                calendar.gotoDate(monthPicker + '-01');
                                calendar.render();
                            }
                        }

                        // Apply employee filter
                        function applyFilter() {
                            employeeFilter = document.getElementById('employeeFilter').value;
                            console.log('Applying filter:', employeeFilter);
                            if (document.getElementById('calendar').style.display === 'block') {
                                calendar.refetchEvents();
                            } else {
                                loadStaffView();
                            }
                        }

                        // Initialize FullCalendar
                        document.addEventListener('DOMContentLoaded', function () {
                            var calendarEl = document.getElementById('calendar');
                            calendar = new FullCalendar.Calendar(calendarEl, {
                                initialView: 'dayGridMonth',
                                headerToolbar: {
                                    left: 'prev,next today',
                                    center: 'title',
                                    right: 'dayGridMonth,timeGridWeek,listMonth'
                                },
                                events: function (fetchInfo, successCallback, failureCallback) {
                                    let fetchUrl = '${pageContext.request.contextPath}/leave/agenda?action=events&start=' +
                                            fetchInfo.startStr.split('T')[0] + '&end=' + fetchInfo.endStr.split('T')[0];
                                    if (employeeFilter)
                                        fetchUrl += '&employeeFilter=' + encodeURIComponent(employeeFilter);
                                    fetch(fetchUrl)
                                            .then(response => {
                                                if (!response.ok)
                                                    throw new Error('Network response was not ok');
                                                return response.json();
                                            })
                                            .then(data => {
                                                console.log('Calendar events:', data);
                                                successCallback(data);
                                            })
                                            .catch(error => {
                                                console.error('Error fetching events:', error);
                                                failureCallback(error);
                                            });
                                },
                                eventClassNames: function (arg) {
                                    console.log('Event status:', arg.event.extendedProps.status);
                                    if (arg.event.extendedProps.status === 'approved') return 'fc-event-approved';
                                    if (arg.event.extendedProps.status === 'rejected') return 'fc-event-rejected';
                                    return '';
                                },
                                eventClick: function (info) {
                                    alert('Leave Request #' + info.event.id + '\n' +
                                            'Title: ' + info.event.title + '\n' +
                                            'Employee: ' + info.event.extendedProps.employee + '\n' +
                                            'From: ' + info.event.startStr + '\n' +
                                            'To: ' + info.event.endStr + '\n' +
                                            'Reason: ' + info.event.extendedProps.reason + '\n' +
                                            'Status: ' + info.event.extendedProps.status + '\n' +
                                            'Processed By: ' + info.event.extendedProps.processedBy);
                                }
                            });

                            // Set default month in date picker
                            var today = new Date();
                            document.getElementById('monthPicker').value = today.toISOString().slice(0, 7);
                            document.getElementById('weekPicker').value = today.toISOString().split('T')[0];

                            // Initialize view based on parameter
                            switchView('${view}' || 'calendar');
                        });
        </script>
    </body>
</html>