 SELECT
    m . *
FROM
    (SELECT
        (SELECT
                    u4.name
                FROM
                    mj_house_unit u1, (SELECT
                    *
                FROM
                    mj_build_unit
                WHERE
                    type = 3) u2, (SELECT
                    *
                FROM
                    mj_build_unit
                WHERE
                    type = 2) u3, (SELECT
                    *
                FROM
                    mj_build_unit
                WHERE
                    type = 1) u4
                WHERE
                    u1.unit_id = u2.id
                        AND u2.parent_id = u3.id
                        AND u4.id = u3.parent_id
                        and u1.id = aa.house_id) build,
            ((SELECT
                    u3.name
                FROM
                    mj_house_unit u1, (SELECT
                    *
                FROM
                    mj_build_unit
                WHERE
                    type = 3) u2, (SELECT
                    *
                FROM
                    mj_build_unit
                WHERE
                    type = 2) u3
                WHERE
                    u1.unit_id = u2.id
                        AND u2.parent_id = u3.id
                        and u1.id = aa.house_id)) unit,
            (SELECT
                    name
                FROM
                    mj_house_unit
                WHERE
                    id = aa.house_id) house,
            aa.house_id,
            bb.id person_id,
            bb.card_no person_card,
            bb.phone person_phone,
            bb.name person_name,
            ifnull(bb.CARD_ID, '未绑定') card_id,
            ifnull((SELECT
                    card_status
                FROM
                    mj_card
                WHERE
                    card_id = bb.CARD_ID), 0) card_status,
            if(bb.CARD_ID IS NULL, '未绑定', '已绑定') is_band,
            (SELECT
                    (SELECT
                                (SELECT
                                            name
                                        FROM
                                            mj_residential
                                        WHERE
                                            id = tt1.residentail_id)
                            FROM
                                mj_build_unit tt2, (SELECT
                                *
                            FROM
                                mj_build_unit
                            WHERE
                                type = 2) tt1
                            WHERE
                                tt2.parent_id = tt1.id
                                    AND tt2.id = t.unit_id)
                FROM
                    mj_house_unit t
                WHERE
                    t.id = aa.house_id) residentail,
            (SELECT
                    (SELECT
                                tt1.residentail_id
                            FROM
                                mj_build_unit tt2, (SELECT
                                *
                            FROM
                                mj_build_unit
                            WHERE
                                type = 2) tt1
                            WHERE
                                tt2.parent_id = tt1.id
                                    AND tt2.id = t.unit_id)
                FROM
                    mj_house_unit t
                WHERE
                    t.id = aa.house_id) residentail_id
    FROM
        (SELECT
        *
    FROM
        r_house_person a) aa
    RIGHT JOIN (SELECT
        b.CARD_ID, a . *
    FROM
        mj_person a
    LEFT JOIN r_person_card b ON a.id = b.person_id) bb ON aa.person_id = bb.id) m
WHERE
    m.residentail_id IS NOT NULL
        AND (m.person_id , m.house_id) IN (SELECT
            r6.person_id, r6.house_id
        FROM
            (SELECT
                id, parent_id
            FROM
                mj_build_unit
            WHERE
                type = 3) r4,
            (SELECT
                id, unit_id
            FROM
                mj_house_unit) r5,
            (SELECT
                person_id, house_id
            FROM
                r_house_person) r6
        WHERE
            r4.id = r5.unit_id
                AND r5.id = r6.house_id
                AND r4.parent_id = 150)