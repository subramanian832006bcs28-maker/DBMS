1--contest leaderboard
*/
SELECT h.hacker_id,
       h.name,
       SUM(ms.max_score) AS total_score
FROM Hackers h
JOIN (
    SELECT hacker_id,
           challenge_id,
           MAX(score) AS max_score
    FROM Submissions
    GROUP BY hacker_id, challenge_id
) ms
ON h.hacker_id = ms.hacker_id
GROUP BY h.hacker_id, h.name
HAVING SUM(ms.max_score) > 0
ORDER BY total_score DESC, h.hacker_id ASC;
exit;


2-- the pads
SELECT name || '(' || SUBSTR(occupation, 1, 1) || ')'
FROM OCCUPATIONS
ORDER BY name;
SELECT 'There are a total of ' || COUNT(*) || ' ' || LOWER(occupation) || 's.'
FROM OCCUPATIONS
GROUP BY occupation
ORDER BY COUNT(*), LOWER(occupation);

3--the blunder
SELECT CEIL(AVG(Salary) - AVG(REPLACE(Salary, '0', ''))) 
FROM EMPLOYEES;

4--top competitor
SELECT
FROM Hackers h
JOIN Submissions s 
    ON h.hacker_id = s.hacker_id
JOIN Challenges c 
    ON s.challenge_id = c.challenge_id
JOIN Difficulty d 
    ON c.difficulty_level = d.difficulty_level
WHERE s.score = d.score
GROUP BY h.hacker_id, h.name
HAVING COUNT(DISTINCT s.challenge_id) > 1
ORDER BY COUNT(DISTINCT s.challenge_id) DESC, h.hacker_id ASC;

5--interviews

SELECT 
    c.contest_id,
    c.hacker_id,
    c.name,
    SUM(ss.total_submissions) AS total_submissions,
    SUM(ss.total_accepted_submissions) AS total_accepted_submissions,
    SUM(vs.total_views) AS total_views,
    SUM(vs.total_unique_views) AS total_unique_views
FROM Contests c
JOIN Colleges col 
    ON c.contest_id = col.contest_id
JOIN Challenges ch 
    ON col.college_id = ch.college_id
LEFT JOIN Submission_Stats ss 
    ON ch.challenge_id = ss.challenge_id
LEFT JOIN View_Stats vs 
    ON ch.challenge_id = vs.challenge_id
GROUP BY c.contest_id, c.hacker_id, c.name
HAVING 
    SUM(ss.total_submissions) > 0 OR
    SUM(ss.total_accepted_submissions) > 0 OR
    SUM(vs.total_views) > 0 OR
    SUM(vs.total_unique_views) > 0
ORDER BY c.contest_id;


6--15 days of learning sql
SELECT 
    s1.submission_date,
    (
        /* Part 1: Count of hackers with a perfect streak */
        SELECT COUNT(DISTINCT s2.hacker_id)
        FROM Submissions s2
        WHERE s2.submission_date = s1.submission_date
          AND (
              SELECT COUNT(DISTINCT s3.submission_date)
              FROM Submissions s3
              WHERE s3.hacker_id = s2.hacker_id
                AND s3.submission_date < s1.submission_date
          ) = DATEDIFF(DAY, '2016-03-01', s1.submission_date)
    ),
    (
        /* Part 2: ID of hacker with max submissions for the day */
        SELECT TOP 1 s4.hacker_id
        FROM Submissions s4
        WHERE s4.submission_date = s1.submission_date
        GROUP BY s4.hacker_id
        ORDER BY COUNT(s4.submission_id) DESC, s4.hacker_id ASC
    ),
    (
        /* Part 3: Name of that top hacker */
        SELECT name 
        FROM Hackers 
        WHERE hacker_id = (
            SELECT TOP 1 s5.hacker_id
            FROM Submissions s5
            WHERE s5.submission_date = s1.submission_date
            GROUP BY s5.hacker_id
            ORDER BY COUNT(s5.submission_id) DESC, s5.hacker_id ASC
        )
    )
FROM (SELECT DISTINCT submission_date FROM Submissions) s1
ORDER BY s1.submission_date;


7--new companies
SELECT 
    c.company_code, 
    c.founder, 
    COUNT(DISTINCT lm.lead_manager_code), 
    COUNT(DISTINCT sm.senior_manager_code), 
    COUNT(DISTINCT m.manager_code), 
    COUNT(DISTINCT e.employee_code)
FROM Company c
JOIN Lead_Manager lm ON c.company_code = lm.company_code
JOIN Senior_Manager sm ON lm.lead_manager_code = sm.lead_manager_code
JOIN Manager m ON sm.senior_manager_code = m.senior_manager_code
JOIN Employee e ON m.manager_code = e.manager_code
GROUP BY c.company_code, c.founder
ORDER BY c.company_code ASC;
exit;

8--the report

SELECT 
    CASE 
        WHEN g.grade >= 8 THEN s.name 
        ELSE 'NULL' 
    END AS name, 
    g.grade, 
    s.marks
FROM Students s
JOIN Grades g ON s.marks BETWEEN g.min_mark AND g.max_mark
ORDER BY 
    g.grade DESC, 
    (CASE WHEN g.grade >= 8 THEN s.name ELSE NULL END) ASC,
    (CASE WHEN g.grade < 8 THEN s.marks ELSE NULL END) ASC;
