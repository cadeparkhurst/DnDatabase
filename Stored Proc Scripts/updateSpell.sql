CREATE PROCEDURE updateSpell
@SpellID int,
@Range int,
@CastingTime int,
@V int=0,
@S int=0,
@M int=0,
@Duration int,
@Concentration int = 0
AS
BEGIN
	if @SpellID is null BEGIN
		PRINT 'ERROR: SpellID cannot be null';
		RETURN (1)
	END
	
	UPDATE dbo.Spell
	SET Range=@Range, CastingTime=@CastingTime,
		V=@V, S=@S, M=@M, Duration=@Duration, Concentration=@Concentration
	WHERE SpellID = @SpellID 
END