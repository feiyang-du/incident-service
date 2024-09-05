CREATE TABLE t_incident
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL UNIQUE, -- Incident title as a unique identifier
    type        INT          NOT NULL,        -- Incident type
    status      INT          NOT NULL,        -- Incident status, 1: Open, 2: In Progress, 3: Resolved
    reporter    VARCHAR(255) NOT NULL,        -- Incident reporter
    handler     VARCHAR(255),                 -- Incident handler
    detail      TEXT,                         -- Incident detail description
    create_time BIGINT       NOT NULL,        -- Incident create time (unix timestamp in milliseconds）
    update_time BIGINT       NOT NULL         -- Incident update time（unix timestamp in milliseconds）
);

CREATE UNIQUE INDEX idx_unique_title ON t_incident (title); -- Used by IncidentRepository.existsByTitle()

