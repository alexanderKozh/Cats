DELETE FROM cat_colors_info;

INSERT INTO cat_colors_info(color, count)
SELECT
    color, count(*)
FROM cats
GROUP BY color;