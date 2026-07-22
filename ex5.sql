

1--combine two tables self-1

SELECT 
    p.firstName,
    p.lastName,
    a.city,
    a.state
FROM Person p
LEFT JOIN Address a
ON p.personId = a.personId;

2--consecutive numbers self-2

SELECT DISTINCT l1.num AS ConsecutiveNums
FROM Logs l1
JOIN Logs l2 
    ON l1.id = l2.id - 1
JOIN Logs l3 
    ON l2.id = l3.id - 1
WHERE l1.num = l2.num
  AND l2.num = l3.num;

3--trips and users self-3

SELECT 
    t.request_at AS Day,
    ROUND(
        SUM(CASE 
                WHEN t.status != 'completed' THEN 1 
                ELSE 0 
            END) / COUNT(*), 
        2
    ) AS `Cancellation Rate`
FROM Trips t
JOIN Users c 
    ON t.client_id = c.users_id
JOIN Users d 
    ON t.driver_id = d.users_id
WHERE c.banned = 'No'
  AND d.banned = 'No'
  AND t.request_at BETWEEN '2013-10-01' AND '2013-10-03'
GROUP BY t.request_at;

4-- managers with 5reports self-2
SELECT e1.name
FROM Employee e1
JOIN Employee e2
    ON e1.id = e2.managerId
GROUP BY e1.id, e1.name
HAVING COUNT(e2.id) >= 5;

5--types of triangle

SELECT 
    CASE
        -- Not a triangle condition
        WHEN A + B <= C OR A + C <= B OR B + C <= A THEN 'Not A Triangle'
        
        -- All sides equal
        WHEN A = B AND B = C THEN 'Equilateral'
        
        -- Any two sides equal
        WHEN A = B OR B = C OR A = C THEN 'Isosceles'
        
        -- All sides different
        ELSE 'Scalene'
    END AS triangle_type
FROM TRIANGLES;

6--occupations

SELECT
    MAX(CASE WHEN Occupation = 'Doctor' THEN Name END) AS Doctor,
    MAX(CASE WHEN Occupation = 'Professor' THEN Name END) AS Professor,
    MAX(CASE WHEN Occupation = 'Singer' THEN Name END) AS Singer,
    MAX(CASE WHEN Occupation = 'Actor' THEN Name END) AS Actor
FROM (
    SELECT 
        Name,
        Occupation,
        ROW_NUMBER() OVER (PARTITION BY Occupation ORDER BY Name) AS rn
    FROM OCCUPATIONS
)
GROUP BY rn
ORDER BY rn;
