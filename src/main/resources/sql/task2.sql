DELETE FROM cats_stat;

INSERT INTO cats_stat(
    tail_length_mean,
    tail_length_median,
    tail_length_mode,

    whiskers_length_mean,
    whiskers_length_median,
    whiskers_length_mode
)
    SELECT
        AVG(tail_length)::numeric(18,2) AS tail_length_mean,
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY tail_length) AS tail_length_median,
        ARRAY_AGG(DISTINCT tail_length) FILTER(
            WHERE tail_length IN (
                SELECT tail_length FROM cats GROUP BY tail_length
                HAVING COUNT(*) = (SELECT count(*) from cats group by tail_length ORDER BY count(*) DESC LIMIT 1)
            )
        ) AS tail_length_mode,

        AVG(whiskers_length)::numeric(18,2) AS whiskers_length_mean,
        PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY whiskers_length) AS whiskers_length_median,
        ARRAY_AGG(DISTINCT whiskers_length) FILTER(
            WHERE whiskers_length IN (
                SELECT whiskers_length FROM cats GROUP BY whiskers_length
                HAVING COUNT(*) = (SELECT count(*) from cats group by whiskers_length ORDER BY count(*) DESC LIMIT 1)
            )
        ) AS whiskers_length_mode
    FROM cats;
